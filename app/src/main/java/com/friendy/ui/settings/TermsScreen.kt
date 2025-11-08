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
fun TermsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Пользовательское Соглашение") },
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
                text = "Универсальное Пользовательское Соглашение",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = """
                    Настоящее Пользовательское Соглашение (далее — «Соглашение») регулирует отношения между пользователем (далее — «Пользователь») и приложением Friendy (далее — «Приложение»).
                    
                    1. Общие положения
                    1.1. Используя Приложение, Пользователь соглашается с условиями настоящего Соглашения.
                    1.2. Приложение предоставляет услуги по использованию чата с искусственным интеллектом.
                    
                    2. Права и обязанности Пользователя
                    2.1. Пользователь обязуется использовать Приложение в соответствии с его назначением.
                    2.2. Пользователь несет ответственность за содержание отправляемых сообщений.
                    
                    3. Конфиденциальность
                    3.1. Все данные хранятся локально на устройстве Пользователя.
                    3.2. Приложение не передает персональные данные третьим лицам.
                    
                    4. Ограничение ответственности
                    4.1. Приложение предоставляется «как есть», без гарантий любого рода.
                    4.2. Разработчик не несет ответственности за возможные последствия использования Приложения.
                    
                    5. Изменения в Соглашении
                    5.1. Разработчик оставляет за собой право изменять настоящее Соглашение.
                    5.2. Продолжение использования Приложения после изменений означает согласие с новыми условиями.
                    
                    Дата последнего обновления: 2025 год.
                """.trimIndent(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 24.sp
            )
        }
    }
}

