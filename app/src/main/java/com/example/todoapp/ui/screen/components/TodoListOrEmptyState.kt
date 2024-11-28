package com.example.todoapp.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.delay


@Composable
fun TodoListOrEmptyState(todos: List<TodoItem>, isLoading: Boolean, lazyListState: LazyListState) {
    var isProgressVisible by remember { mutableStateOf(isLoading) }

    // Use LaunchedEffect to introduce a minimum duration for the progress indicator
    LaunchedEffect(isLoading) {
        if (isLoading) {
            // Show the progress bar for at least 3 seconds, even if loading is fast
            delay(3000) // Wait for 3 seconds
            isProgressVisible = false // After 3 seconds, hide the progress indicator
        } else {
            // If loading finishes early, hide the progress indicator immediately
            isProgressVisible = false
        }
    }

    if (isProgressVisible) {
        // Show progress bar or skeleton UI here
        Box(
            modifier = Modifier
                .fillMaxSize(), // Fill the parent size
            contentAlignment = Alignment.Center // Center the progress indicator
        ) {
            CircularProgressIndicator()
        }
    } else if (todos.isEmpty()) {
        EmptyState()
    } else {
        TodoList(todos, lazyListState)
    }
}

