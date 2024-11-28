package com.example.todoapp.data.repository

import com.example.todoapp.data.database.TodoDao
import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val dao: TodoDao) {
    fun getTodos(): Flow<List<TodoItem>> = dao.getAllTodos()
    suspend fun addTodo(todo: TodoItem) = dao.insert(todo)
}
