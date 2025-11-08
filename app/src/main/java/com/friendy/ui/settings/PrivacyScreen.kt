package com.friendy.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Политика Конфиденциальности") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Политика Конфиденциальности",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = """
                    Настоящая Политика Конфиденциальности описывает, как приложение Friendy (далее — «Приложение») обрабатывает и защищает данные пользователей.
                    
                    1. Сбор данных
                    1.1. Приложение собирает только данные, необходимые для его функционирования.
                    1.2. Все сообщения и история чатов хранятся исключительно на устройстве Пользователя.
                    
                    2. Хранение данных
                    2.1. Все данные хранятся локально на устройстве Пользователя с использованием локальной базы данных.
                    2.2. Данные не передаются на внешние серверы, за исключением запросов к API искусственного интеллекта.
                    
                    3. Использование данных
                    3.1. Данные используются исключительно для функционирования Приложения.
                    3.2. Приложение не использует данные для рекламы или передачи третьим лицам.
                    
                    4. Безопасность
                    4.1. Приложение использует стандартные механизмы безопасности Android для защиты данных.
                    4.2. Пользователь несет ответственность за безопасность своего устройства.
                    
                    5. Права пользователя
                    5.1. Пользователь может в любой момент удалить все данные через функцию удаления в Приложении.
                    5.2. Все данные удаляются безвозвратно и не могут быть восстановлены.
                    
                    6. Изменения в Политике
                    6.1. Разработчик оставляет за собой право изменять настоящую Политику.
                    6.2. Продолжение использования Приложения после изменений означает согласие с новой Политикой.
                    
                    7. Контакты
                    7.1. По вопросам конфиденциальности обращайтесь через настройки Приложения.
                    
                    Дата последнего обновления: 2025 год.
                """.trimIndent(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 24.sp
            )
        }
    }
}

