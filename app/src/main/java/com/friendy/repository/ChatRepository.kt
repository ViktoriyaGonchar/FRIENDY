package com.friendy.repository

import android.content.Context
import com.friendy.api.ApiClient
import com.friendy.api.ApiConfig
import com.friendy.api.model.ChatRequest
import com.friendy.data.dao.MessageDao
import com.friendy.data.model.Message
import com.friendy.util.NetworkUtils
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class ChatRepository(
    private val messageDao: MessageDao,
    private val context: Context
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun getMessagesByDate(date: String): Flow<List<Message>> {
        return messageDao.getMessagesByDate(date)
    }

    fun getAllDates(): Flow<List<String>> {
        return messageDao.getAllDates()
    }

    suspend fun sendMessage(text: String): String {
        // Сохраняем сообщение пользователя
        val currentDate = dateFormat.format(Date())
        val timestamp = System.currentTimeMillis()
        val userMessage = Message(
            text = text,
            isFromUser = true,
            date = currentDate,
            timestamp = timestamp
        )
        messageDao.insertMessage(userMessage)

        // Проверяем наличие интернет-соединения
        if (!NetworkUtils.isNetworkAvailable(context)) {
            val errorMessage = "Нет подключения к интернету. Проверьте соединение и попробуйте снова."
            val aiMessage = Message(
                text = errorMessage,
                isFromUser = false,
                date = currentDate,
                timestamp = System.currentTimeMillis()
            )
            messageDao.insertMessage(aiMessage)
            return errorMessage
        }

        // Отправляем запрос к API
        return try {
            val request = ChatRequest(
                message = text,
                clientId = ApiConfig.CLIENT_ID
            )
            // ═══ СТРОКА 50: Формат авторизации (при необходимости измените "Bearer" на другой формат) ═══
            val response = ApiClient.service.sendMessage(
                apiKey = "Bearer ${ApiConfig.API_KEY}",
                request = request
            )

            // Сохраняем ответ ИИ
            val aiMessage = Message(
                text = response.response,
                isFromUser = false,
                date = currentDate,
                timestamp = System.currentTimeMillis()
            )
            messageDao.insertMessage(aiMessage)

            response.response
        } catch (e: Exception) {
            // В случае ошибки возвращаем сообщение об ошибке
            val errorMessage = when {
                !NetworkUtils.isNetworkConnected(context) -> 
                    "Нет подключения к интернету. Проверьте соединение и попробуйте снова."
                else -> 
                    "Ошибка подключения к API: ${e.message ?: "Неизвестная ошибка"}"
            }
            val aiMessage = Message(
                text = errorMessage,
                isFromUser = false,
                date = currentDate,
                timestamp = System.currentTimeMillis()
            )
            messageDao.insertMessage(aiMessage)
            errorMessage
        }
    }

    suspend fun deleteMessagesByDate(date: String) {
        messageDao.deleteMessagesByDate(date)
    }

    fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }

    fun formatDateForDisplay(dateString: String): String {
        return try {
            val date = dateFormat.parse(dateString)
            val displayFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru", "RU"))
            displayFormat.format(date ?: Date())
        } catch (e: Exception) {
            dateString
        }
    }
}

