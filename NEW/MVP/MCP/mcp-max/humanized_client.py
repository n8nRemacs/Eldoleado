"""
Humanized MAX.ru Bot API Client - обёртка с задержками и typing indicator

Задержки перед ответом:
- Рабочее время (9:00-18:00): 3-7 секунд
- Нерабочее время (18:00-00:00, 7:00-9:00): 15-45 секунд
- Ночное время (00:00-07:00): 1-3 минуты

+ Показывает "печатает..." перед отправкой
"""

import asyncio
import random
import logging
from datetime import datetime, time as dt_time
from typing import Optional, List, Callable, Awaitable

from max_client import MaxClient, MaxAPIError

logger = logging.getLogger(__name__)


class TimeWindow:
    """Определение временных окон"""

    # Рабочее время
    WORK_START = dt_time(9, 0)
    WORK_END = dt_time(18, 0)

    # Ночное время
    NIGHT_START = dt_time(0, 0)
    NIGHT_END = dt_time(7, 0)

    @classmethod
    def get_current_period(cls) -> str:
        """Определить текущий период: 'work', 'off', 'night'"""
        now = datetime.now().time()

        # Ночь: 00:00 - 07:00
        if cls.NIGHT_START <= now < cls.NIGHT_END:
            return 'night'

        # Рабочее: 09:00 - 18:00
        if cls.WORK_START <= now < cls.WORK_END:
            return 'work'

        # Нерабочее: 07:00-09:00, 18:00-00:00
        return 'off'


class HumanizedMaxClient:
    """
    Обёртка над MaxClient с человеческими задержками и typing indicator

    Использование:
        client = HumanizedMaxClient(access_token="your_token")
        await client.connect()

        # Отправка с typing indicator и задержкой по времени суток
        await client.send_message_humanized(chat_id=123, text="Привет!")

        # Принудительно как в рабочее время
        await client.send_message_humanized(chat_id=123, text="Срочно!", force_period='work')

        await client.close()
    """

    # Задержки в секундах (min, max)
    DELAYS = {
        'work': (3, 7),           # Рабочее время: 3-7 сек
        'off': (15, 45),          # Нерабочее: 15-45 сек
        'night': (60, 180),       # Ночь: 1-3 минуты
    }

    # Дополнительная задержка на "печатание" (символов в секунду)
    TYPING_SPEED = 5.0  # ~5 символов в секунду

    # Интервал обновления typing indicator (секунды)
    TYPING_REFRESH_INTERVAL = 5.0

    def __init__(
        self,
        access_token: str,
        typing_indicator: bool = True,
        typing_simulation: bool = True,
        on_delay_start: Callable[[str, float], Awaitable[None]] = None,
        custom_delays: dict = None
    ):
        """
        Args:
            access_token: Bot access token from MasterBot
            typing_indicator: Показывать "печатает..." в чате
            typing_simulation: Добавлять задержку на "печатание" по длине текста
            on_delay_start: Async callback при начале задержки (period, seconds)
            custom_delays: Свои задержки {'work': (min, max), 'off': ..., 'night': ...}
        """
        self.client = MaxClient(access_token=access_token)
        self.typing_indicator = typing_indicator
        self.typing_simulation = typing_simulation
        self.on_delay_start = on_delay_start

        if custom_delays:
            self.DELAYS = {**self.DELAYS, **custom_delays}

    async def connect(self):
        """Initialize HTTP client."""
        await self.client.connect()

    async def close(self):
        """Close HTTP client."""
        await self.client.close()

    def _get_delay(self, period: str = None, text_length: int = 0) -> float:
        """
        Рассчитать задержку

        Args:
            period: Принудительно период ('work', 'off', 'night') или None для авто
            text_length: Длина текста для симуляции печатания

        Returns:
            Задержка в секундах
        """
        if period is None:
            period = TimeWindow.get_current_period()

        min_delay, max_delay = self.DELAYS.get(period, self.DELAYS['work'])
        base_delay = random.uniform(min_delay, max_delay)

        # Добавляем время на "печатание"
        typing_delay = 0
        if self.typing_simulation and text_length > 0:
            typing_delay = text_length / self.TYPING_SPEED
            # Добавляем случайность ±20%
            typing_delay *= random.uniform(0.8, 1.2)

        return base_delay + typing_delay

    async def _do_delay_with_typing(
        self,
        delay: float,
        period: str,
        chat_id: int = None,
        user_id: int = None
    ):
        """
        Выполнить задержку с typing indicator

        Args:
            delay: Задержка в секундах
            period: Период времени
            chat_id: ID чата для typing indicator (если есть)
            user_id: ID пользователя (для DM)
        """
        if self.on_delay_start:
            await self.on_delay_start(period, delay)

        logger.info(f"Humanized delay: {delay:.1f}s (period: {period})")

        if self.typing_indicator and chat_id:
            # Показываем typing indicator с периодическим обновлением
            remaining = delay
            while remaining > 0:
                try:
                    await self.client.send_action(chat_id, action="typing_on")
                except Exception as e:
                    logger.warning(f"Failed to send typing action: {e}")

                sleep_time = min(self.TYPING_REFRESH_INTERVAL, remaining)
                await asyncio.sleep(sleep_time)
                remaining -= sleep_time
        else:
            # Просто ждём
            await asyncio.sleep(delay)

    async def send_message_humanized(
        self,
        chat_id: int = None,
        user_id: int = None,
        text: str = None,
        attachments: List[dict] = None,
        link: dict = None,
        notify: bool = True,
        format: str = None,
        disable_link_preview: bool = False,
        force_period: str = None,
        skip_delay: bool = False
    ) -> dict:
        """
        Отправить сообщение с человеческой задержкой и typing indicator

        Args:
            chat_id: Chat ID (for group chats)
            user_id: User ID (for direct messages)
            text: Message text
            attachments: List of attachments
            link: Link preview object
            notify: Enable notifications
            format: Text format ('markdown' or 'html')
            disable_link_preview: Disable link preview
            force_period: Принудительно использовать период ('work', 'off', 'night')
            skip_delay: Пропустить задержку (для срочных)

        Returns:
            Ответ API
        """
        period = force_period or TimeWindow.get_current_period()

        if not skip_delay:
            text_length = len(text) if text else 0
            delay = self._get_delay(period, text_length)
            await self._do_delay_with_typing(delay, period, chat_id, user_id)

        return await self.client.send_message(
            chat_id=chat_id,
            user_id=user_id,
            text=text,
            attachments=attachments,
            link=link,
            notify=notify,
            format=format,
            disable_link_preview=disable_link_preview
        )

    async def mark_seen_humanized(
        self,
        chat_id: int,
        force_period: str = None,
        skip_delay: bool = False
    ) -> dict:
        """
        Пометить сообщения как прочитанные с задержкой

        Args:
            chat_id: ID чата
            force_period: Принудительно период
            skip_delay: Пропустить задержку
        """
        period = force_period or TimeWindow.get_current_period()

        if not skip_delay:
            # Для прочтения используем меньшую задержку (1/3 от отправки)
            min_d, max_d = self.DELAYS.get(period, self.DELAYS['work'])
            delay = random.uniform(min_d / 3, max_d / 3)
            await asyncio.sleep(delay)

        return await self.client.send_action(chat_id, action="mark_seen")

    # === Проксирование методов без задержки ===

    async def get_me(self) -> dict:
        """Get bot info."""
        return await self.client.get_me()

    async def get_chats(self, *args, **kwargs) -> dict:
        """Get list of chats."""
        return await self.client.get_chats(*args, **kwargs)

    async def get_chat(self, chat_id: int) -> dict:
        """Get chat by ID."""
        return await self.client.get_chat(chat_id)

    async def get_chat_members(self, *args, **kwargs) -> dict:
        """Get chat members."""
        return await self.client.get_chat_members(*args, **kwargs)

    async def send_action(self, chat_id: int, action: str = "typing_on") -> dict:
        """Send action (typing indicator)."""
        return await self.client.send_action(chat_id, action)

    async def edit_message(self, *args, **kwargs) -> dict:
        """Edit message."""
        return await self.client.edit_message(*args, **kwargs)

    async def delete_message(self, message_id: str) -> dict:
        """Delete message."""
        return await self.client.delete_message(message_id)

    async def get_message(self, message_id: str) -> dict:
        """Get message by ID."""
        return await self.client.get_message(message_id)

    async def get_updates(self, *args, **kwargs) -> dict:
        """Get updates via long polling."""
        return await self.client.get_updates(*args, **kwargs)

    async def subscribe(self, *args, **kwargs) -> dict:
        """Subscribe to webhook."""
        return await self.client.subscribe(*args, **kwargs)

    async def unsubscribe(self, url: str) -> dict:
        """Unsubscribe from webhook."""
        return await self.client.unsubscribe(url)

    async def get_subscriptions(self) -> dict:
        """Get current subscriptions."""
        return await self.client.get_subscriptions()

    async def answer_callback(self, *args, **kwargs) -> dict:
        """Answer to callback button."""
        return await self.client.answer_callback(*args, **kwargs)


# === Пример использования ===

async def main():
    import sys

    async def on_delay(period: str, seconds: float):
        print(f"⏳ Ожидание {seconds:.1f}с ({period})...")

    if len(sys.argv) < 2:
        print("Usage: python humanized_client.py <access_token> [chat_id] [message]")
        print("\nПример:")
        print("  python humanized_client.py 'xxx' 123456 'Добрый день!'")
        print("\nТекущий период:", TimeWindow.get_current_period())
        return

    access_token = sys.argv[1]
    client = HumanizedMaxClient(access_token=access_token, on_delay_start=on_delay)

    print(f"🕐 Текущее время: {datetime.now().strftime('%H:%M:%S')}")
    print(f"📅 Период: {TimeWindow.get_current_period()}")

    try:
        await client.connect()

        if len(sys.argv) >= 4:
            chat_id = int(sys.argv[2])
            message = sys.argv[3]

            print(f"\n📤 Отправка в {chat_id}: {message[:50]}...")
            result = await client.send_message_humanized(chat_id=chat_id, text=message)
            print(f"✅ Отправлено: {result}")
        else:
            # Показать инфо о боте
            print("\n🤖 Получение информации о боте...")
            me = await client.get_me()
            print(f"  Бот: {me}")

            print("\n📥 Загрузка чатов...")
            chats = await client.get_chats(count=5)
            for ch in chats.get('chats', [])[:5]:
                print(f"  - {ch.get('chat_id')}: {ch.get('title', 'N/A')}")

    finally:
        await client.close()


if __name__ == '__main__':
    asyncio.run(main())
