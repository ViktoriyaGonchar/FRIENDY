package com.friendy.api

/**
 * ═══════════════════════════════════════════════════════════════
 * КОНФИГУРАЦИЯ API - НАСТРОЙКА ЗДЕСЬ!
 * ═══════════════════════════════════════════════════════════════
 * 
 * Для работы чата необходимо заменить значения ниже на ваши:
 * 
 * 1. API_KEY (строка 24) - ваш API ключ для аутентификации
 * 2. CLIENT_ID (строка 27) - идентификатор клиента
 * 3. API_ENDPOINT (строка 30) - URL вашего API эндпоинта
 * 
 * Пример:
 *   const val API_KEY = "sk-1234567890abcdef..."
 *   const val CLIENT_ID = "client_12345"
 *   const val API_ENDPOINT = "https://api.openai.com/v1/chat/completions"
 * 
 * После изменения сохраните файл и перезапустите приложение.
 * ═══════════════════════════════════════════════════════════════
 */
object ApiConfig {
    // ═══ СТРОКА 24: Замените на ваш API ключ ═══
    const val API_KEY = "your_api_key_here"
    
    // ═══ СТРОКА 27: Замените на ваш Client ID ═══
    const val CLIENT_ID = "your_client_id_here"
    
    // ═══ СТРОКА 30: Замените на URL вашего API ═══
    const val API_ENDPOINT = "https://api.example.com/v1/chat"
}

