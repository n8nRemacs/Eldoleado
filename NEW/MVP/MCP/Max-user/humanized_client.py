"""
Humanized MAX User API Client - обёртка с задержками и typing indicator

Задержки перед ответом:
- Рабочее время (9:00-18:00): 3-7 секунд
- Нерабочее время (18:00-00:00, 7:00-9:00): 15-45 секунд
- Ночное время (00:00-07:00): 1-3 минуты

+ Показывает "печатает..." перед отправкой через SET_TYPING (opcode 51)
"""

import asyncio
import random
import logging
from datetime import datetime, time as dt_time
from typing import Optional, Callable, Awaitable

from max_user_client import MaxUserClient, MaxUserAPIError

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


class HumanizedMaxUserClient:
    """
    Обёртка над MaxUserClient с человеческими задержками и typing indicator

    Использование:
        client = HumanizedMaxUserClient()
        await client.connect()
        await client.login_by_token(token, device_id)

        # Отправка с typing indicator и задержкой по времени суток
        await client.send_message_humanized(chat_id=123, text="Привет!")

        await client.close()

    Задержки:
        - Рабочее время (9:00-18:00): 3-7 сек
        - Нерабочее время: 15-45 сек
        - Ночное время (00:00-07:00): 1-3 минуты
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
    TYPING_REFRESH_INTERVAL = 4.0  # MAX typing timeout ~5s, refresh at 4s

    def __init__(
        self,
        typing_indicator: bool = True,
        typing_simulation: bool = True,
        on_delay_start: Callable[[str, float], Awaitable[None]] = None,
        on_message: Callable[[dict], Awaitable[None]] = None,
        on_presence: Callable[[dict], Awaitable[None]] = None,
        custom_delays: dict = None
    ):
        """
        Args:
            typing_indicator: Показывать "печатает..." в чате (SET_TYPING)
            typing_simulation: Добавлять задержку на "печатание" по длине текста
            on_delay_start: Async callback при начале задержки (period, seconds)
            on_message: Callback для входящих сообщений
            on_presence: Callback для статуса онлайн
            custom_delays: Свои задержки {'work': (min, max), ...}
        """
        self.client = MaxUserClient(on_message=on_message, on_presence=on_presence)
        self.typing_indicator = typing_indicator
        self.typing_simulation = typing_simulation
        self.on_delay_start = on_delay_start

        if custom_delays:
            self.DELAYS = {**self.DELAYS, **custom_delays}

    async def connect(self):
        """Connect to MAX WebSocket."""
        await self.client.connect()

    async def close(self):
        """Close connection."""
        await self.client.close()

    @property
    def profile(self) -> dict:
        """Current user profile."""
        return self.client.profile

    @property
    def login_token(self) -> str:
        """Login token for re-auth."""
        return self.client.login_token

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
        chat_id: int
    ):
        """
        Выполнить задержку с typing indicator

        Args:
            delay: Задержка в секундах
            period: Период времени
            chat_id: ID чата для typing indicator
        """
        if self.on_delay_start:
            await self.on_delay_start(period, delay)

        logger.info(f"Humanized delay: {delay:.1f}s (period: {period})")

        if self.typing_indicator and chat_id:
            # Показываем typing indicator с периодическим обновлением
            remaining = delay
            while remaining > 0:
                try:
                    await self.client.set_typing(chat_id, typing=True)
                except Exception as e:
                    logger.warning(f"Failed to send typing: {e}")

                sleep_time = min(self.TYPING_REFRESH_INTERVAL, remaining)
                await asyncio.sleep(sleep_time)
                remaining -= sleep_time

            # Отключаем typing перед отправкой
            try:
                await self.client.set_typing(chat_id, typing=False)
            except Exception:
                pass
        else:
            await asyncio.sleep(delay)

    # ========== Humanized Methods ==========

    async def send_message_humanized(
        self,
        chat_id: int,
        text: str,
        attaches: list = None,
        reply_to: str = None,
        notify: bool = True,
        force_period: str = None,
        skip_delay: bool = False
    ) -> dict:
        """
        Отправить сообщение с человеческой задержкой и typing indicator

        Args:
            chat_id: Chat ID
            text: Message text
            attaches: List of attachments
            reply_to: Message ID to reply to
            notify: Enable notifications
            force_period: Принудительно период ('work', 'off', 'night')
            skip_delay: Пропустить задержку

        Returns:
            Sent message info
        """
        period = force_period or TimeWindow.get_current_period()

        if not skip_delay:
            delay = self._get_delay(period, len(text))
            await self._do_delay_with_typing(delay, period, chat_id)

        return await self.client.send_message(
            chat_id=chat_id,
            text=text,
            attaches=attaches,
            reply_to=reply_to,
            notify=notify
        )

    async def read_message_humanized(
        self,
        chat_id: int,
        message_id: str = None,
        force_period: str = None,
        skip_delay: bool = False
    ) -> dict:
        """
        Пометить сообщения как прочитанные с задержкой

        Args:
            chat_id: Chat ID
            message_id: Message ID (optional)
            force_period: Принудительно период
            skip_delay: Пропустить задержку
        """
        period = force_period or TimeWindow.get_current_period()

        if not skip_delay:
            # Для прочтения используем меньшую задержку (1/3 от отправки)
            min_d, max_d = self.DELAYS.get(period, self.DELAYS['work'])
            delay = random.uniform(min_d / 3, max_d / 3)
            logger.info(f"Read delay: {delay:.1f}s (period: {period})")
            await asyncio.sleep(delay)

        return await self.client.read_message(chat_id, message_id)

    # ========== Auth (proxied, no delay) ==========

    async def start_auth(self, phone: str, language: str = "ru") -> dict:
        """Start SMS authentication."""
        return await self.client.start_auth(phone, language)

    async def verify_code(self, token: str, code: str) -> dict:
        """Verify SMS code."""
        return await self.client.verify_code(token, code)

    async def verify_2fa(self, token: str, password: str) -> dict:
        """Verify 2FA password."""
        return await self.client.verify_2fa(token, password)

    async def login_by_token(self, token: str, device_id: str = None, chats_count: int = 40) -> dict:
        """Login using saved token."""
        return await self.client.login_by_token(token, device_id, chats_count)

    # ========== Chat Methods (proxied, no delay) ==========

    async def get_chats(self, count: int = 40, offset: int = 0) -> dict:
        """Get list of chats."""
        return await self.client.get_chats(count, offset)

    async def get_chat(self, chat_id: int, count: int = 50) -> dict:
        """Get chat with message history."""
        return await self.client.get_chat(chat_id, count)

    async def set_typing(self, chat_id: int, typing: bool = True) -> dict:
        """Set typing indicator manually."""
        return await self.client.set_typing(chat_id, typing)

    async def get_contacts(self, user_ids: list) -> dict:
        """Get user info by IDs."""
        return await self.client.get_contacts(user_ids)

    # ========== Message Methods (proxied, no delay) ==========

    async def edit_message(self, chat_id: int, message_id: str, text: str) -> dict:
        """Edit message."""
        return await self.client.edit_message(chat_id, message_id, text)

    async def delete_message(self, chat_id: int, message_id: str) -> dict:
        """Delete message."""
        return await self.client.delete_message(chat_id, message_id)


# === Пример использования ===

async def main():
    import sys

    async def on_delay(period: str, seconds: float):
        print(f"⏳ Ожидание {seconds:.1f}с ({period})...")

    async def on_message(payload: dict):
        chat_id = payload.get("chatId")
        text = payload.get("message", {}).get("text", "")
        print(f"📨 Новое сообщение в {chat_id}: {text[:50]}")

    print(f"🕐 Текущее время: {datetime.now().strftime('%H:%M:%S')}")
    print(f"📅 Период: {TimeWindow.get_current_period()}")

    if len(sys.argv) < 3:
        print("\nUsage: python humanized_client.py <login_token> <device_id> [chat_id] [message]")
        print("\nПример:")
        print("  python humanized_client.py 'token' 'uuid' 123456 'Добрый день!'")
        return

    token = sys.argv[1]
    device_id = sys.argv[2]

    client = HumanizedMaxUserClient(
        on_delay_start=on_delay,
        on_message=on_message
    )

    try:
        await client.connect()
        print("✅ Подключено к MAX")

        await client.login_by_token(token, device_id)
        print(f"✅ Залогинен как: {client.profile.get('name', 'Unknown')}")

        if len(sys.argv) >= 5:
            chat_id = int(sys.argv[3])
            message = sys.argv[4]

            print(f"\n📤 Отправка в {chat_id}: {message[:50]}...")
            result = await client.send_message_humanized(chat_id=chat_id, text=message)
            print(f"✅ Отправлено: {result}")
        else:
            # Показать чаты
            print("\n📥 Загрузка чатов...")
            chats = await client.get_chats(count=5)
            for ch in chats.get('chats', [])[:5]:
                print(f"  - {ch.get('chatId')}: {ch.get('title', 'N/A')}")

    finally:
        await client.close()


if __name__ == '__main__':
    asyncio.run(main())
