package com.friendy.data.dao

import androidx.room.*
import com.friendy.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE date = :date ORDER BY timestamp ASC")
    fun getMessagesByDate(date: String): Flow<List<Message>>

    @Query("SELECT DISTINCT date FROM messages ORDER BY date DESC")
    fun getAllDates(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("DELETE FROM messages WHERE date = :date")
    suspend fun deleteMessagesByDate(date: String)
}

