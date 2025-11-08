package com.friendy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.friendy.data.PreferencesManager
import com.friendy.data.database.AppDatabase
import com.friendy.repository.ChatRepository
import com.friendy.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Инициализация компонентов
        val database = AppDatabase.getDatabase(this)
        val messageDao = database.messageDao()
        val chatRepository = ChatRepository(messageDao, this)
        val preferencesManager = PreferencesManager(this)

        setContent {
            MainScreen(
                preferencesManager = preferencesManager,
                chatRepository = chatRepository,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}