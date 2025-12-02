# MCP Setup для Android-разработки

## Установленные MCP серверы

### 1. **mcpandroidbuild** - Сборка и тестирование Android проектов
- **Назначение**: Автоматизация сборки, запуска unit-тестов и instrumented-тестов
- **Установка**: `pip install mcpandroidbuild`
- **Инструменты**:
  - `build` - компиляция Android проекта (нужно указать полный путь к проекту)
  - `test` - запуск unit-тестов
  - `instrumentedTest` - запуск instrumented-тестов на устройстве/эмуляторе

### 2. **memory** - Контекстная память для сессий
- **Назначение**: Сохранение контекста между сессиями Claude Code
- **Установка**: Автоматически через npx
- **Использование**: Автоматическое сохранение важной информации о проекте

### 3. **mobile-mcp** - Автоматизация мобильных устройств
- **Назначение**: Управление Android и iOS устройствами через ADB/другие инструменты
- **Установка**: `npm install -g mobile-mcp`
- **Возможности**:
  - Управление эмуляторами и реальными устройствами
  - Автоматизация UI-тестирования
  - Скрапинг данных с мобильных устройств
  - Работа с симуляторами iOS

## Конфигурация в Claude Code

Конфигурация находится в файле `~/.claude.json` (Windows: `C:\Users\User\.claude.json`).

```json
{
  "mcpServers": {
    "mcpandroidbuild": {
      "command": "python",
      "args": ["-m", "mcpandroidbuild"]
    },
    "memory": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-memory"]
    },
    "mobile-mcp": {
      "type": "stdio",
      "command": "npx",
      "args": ["mobile-mcp"],
      "env": {
        "ANDROID_SDK_ROOT": "C:\\Users\\User\\AppData\\Local\\Android\\Sdk"
      }
    }
  }
}
```

## Проверка статуса серверов

```bash
claude mcp list
```

Результат:
```
✓ mcpandroidbuild: python -m mcpandroidbuild - Connected
✓ memory: npx -y @modelcontextprotocol/server-memory - Connected
✓ mobile-mcp: npx mobile-mcp - Connected
```

## Примеры использования

### Сборка Android проекта
```
Claude, собери Android проект в директории C:\Users\User\Dropbox\BattCRMApp
```

### Запуск тестов
```
Claude, запусти unit-тесты для проекта
```

### Работа с эмулятором
```
Claude, запусти эмулятор Android и установи приложение
```

## Дополнительные MCP серверы для Android

### Альтернативы (не установлены, но могут быть полезны):

1. **@landicefu/android-adb-mcp-server**
   - Прямое управление через ADB
   - Установка: `npm install -g @landicefu/android-adb-mcp-server`
   - Требует: ADB в PATH

2. **Android-MCP** (CursorTouch)
   - Легковесный сервер для QA-автоматизации
   - Python-based
   - GitHub: https://github.com/CursorTouch/Android-MCP

3. **JetBrains MCP Proxy**
   - Интеграция с Android Studio
   - Требует: Android Studio Narwhal Canary

## Troubleshooting

### MCP сервер не подключается
1. Проверьте установку: `pip list | grep mcpandroidbuild` или `npm list -g | grep mobile-mcp`
2. Проверьте логи: `claude mcp list --verbose`
3. Перезапустите Claude Code

### ADB не найден
- Установите Android SDK Platform Tools
- Добавьте в PATH: `C:\Users\User\AppData\Local\Android\Sdk\platform-tools`
- Или используйте переменную окружения `ANDROID_SDK_ROOT`

### Проблемы с mobile-mcp
- Убедитесь, что `ANDROID_SDK_ROOT` указывает на правильную директорию SDK
- Проверьте доступность adb: `adb devices`

## Полезные ссылки

- [mcpandroidbuild на LobeHub](https://lobehub.com/mcp/shenghaiwang-androidbuild)
- [mobile-mcp GitHub](https://github.com/mobile-next/mobile-mcp)
- [MCP Documentation](https://modelcontextprotocol.io/)
- [Claude Code MCP Guide](https://docs.claude.com/en/docs/claude-code/mcp)
