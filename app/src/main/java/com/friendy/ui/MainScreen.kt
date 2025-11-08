package com.friendy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.friendy.data.PreferencesManager
import com.friendy.repository.ChatRepository
import com.friendy.ui.chat.ChatScreen
import com.friendy.ui.history.HistoryScreen
import com.friendy.ui.settings.PrivacyScreen
import com.friendy.ui.settings.SettingsScreen
import com.friendy.ui.settings.TermsScreen
import com.friendy.ui.theme.AppTheme
import com.friendy.ui.theme.FriendyTheme
import com.friendy.util.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Screen {
    CHAT,
    SETTINGS,
    TERMS,
    PRIVACY
}

@Composable
fun MainScreen(
    preferencesManager: PreferencesManager,
    chatRepository: ChatRepository,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var currentTheme by remember { mutableStateOf(AppTheme.PRIMARY) }
    var currentScreen by remember { mutableStateOf(Screen.CHAT) }
    var isHistoryOpen by remember { mutableStateOf(false) }
    
    val currentDate = remember { chatRepository.getCurrentDate() }
    var activeDate by remember { mutableStateOf(currentDate) }
    
    val messages by chatRepository.getMessagesByDate(activeDate).collectAsState(initial = emptyList())
    val dates by chatRepository.getAllDates().collectAsState(initial = emptyList())
    
    val context = LocalContext.current
    var isNetworkAvailable by remember { mutableStateOf(NetworkUtils.isNetworkAvailable(context)) }
    
    // Отслеживаем изменения состояния сети
    LaunchedEffect(Unit) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            NetworkUtils.networkStateFlow(context).collect { available ->
                isNetworkAvailable = available
            }
        } else {
            // Для старых версий Android периодически проверяем состояние
            while (true) {
                isNetworkAvailable = NetworkUtils.isNetworkAvailable(context)
                delay(2000) // Проверка каждые 2 секунды
            }
        }
    }

    // Подписываемся на изменения темы
    LaunchedEffect(Unit) {
        preferencesManager.themeFlow.collect { theme ->
            currentTheme = theme
        }
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.75f

    FriendyTheme(theme = currentTheme) {
        Box(modifier = modifier.fillMaxSize()) {
            // Основной контент
            when (currentScreen) {
                Screen.CHAT -> {
                    ChatScreen(
                        messages = messages,
                        onSendMessage = { text ->
                            scope.launch {
                                chatRepository.sendMessage(text)
                            }
                        },
                        onMenuClick = { isHistoryOpen = true },
                        onSettingsClick = { currentScreen = Screen.SETTINGS },
                        isNetworkAvailable = isNetworkAvailable,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Screen.SETTINGS -> {
                    SettingsScreen(
                        currentTheme = currentTheme,
                        onThemeChange = { theme ->
                            scope.launch {
                                preferencesManager.setTheme(theme)
                            }
                        },
                        onBackClick = { currentScreen = Screen.CHAT },
                        onTermsClick = { currentScreen = Screen.TERMS },
                        onPrivacyClick = { currentScreen = Screen.PRIVACY },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Screen.TERMS -> {
                    TermsScreen(
                        onBackClick = { currentScreen = Screen.SETTINGS },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Screen.PRIVACY -> {
                    PrivacyScreen(
                        onBackClick = { currentScreen = Screen.SETTINGS },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Боковое меню (история)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(drawerWidth)
                    .offset(x = if (isHistoryOpen) 0.dp else -drawerWidth)
                    .background(MaterialTheme.colorScheme.surface)
                    .zIndex(1f)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            if (dragAmount > 0 && !isHistoryOpen) {
                                // Свайп вправо открывает меню
                                if (dragAmount > 50) {
                                    isHistoryOpen = true
                                }
                            } else if (dragAmount < 0 && isHistoryOpen) {
                                // Свайп влево закрывает меню
                                if (dragAmount < -50) {
                                    isHistoryOpen = false
                                }
                            }
                        }
                    }
            ) {
                HistoryScreen(
                    dates = dates,
                    onDateClick = { date ->
                        activeDate = date
                        isHistoryOpen = false
                    },
                    onDeleteDate = { date ->
                        scope.launch {
                            chatRepository.deleteMessagesByDate(date)
                            if (activeDate == date) {
                                activeDate = currentDate
                            }
                        }
                    },
                    onClose = { isHistoryOpen = false },
                    formatDate = { chatRepository.formatDateForDisplay(it) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Затемнение фона при открытом меню
            if (isHistoryOpen) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                        .zIndex(0.5f)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { change, dragAmount ->
                                if (dragAmount < -50) {
                                    isHistoryOpen = false
                                }
                            }
                        }
                )
            }
        }
    }
}

