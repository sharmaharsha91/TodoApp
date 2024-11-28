package com.example.todoapp.ui.screen.components


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.todoapp.domain.model.TodoItem

@Composable
fun TodoList(todos: List<TodoItem>, lazyListState: LazyListState) {
    LazyColumn(state = lazyListState) {
        items(todos) { todo ->
            TodoItemCard(todo = todo)
        }
    }
}

