package com.example.todoapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.domain.model.TodoItem
import com.example.todoapp.ui.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoItem)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllTodos(): Flow<List<TodoItem>>
}

