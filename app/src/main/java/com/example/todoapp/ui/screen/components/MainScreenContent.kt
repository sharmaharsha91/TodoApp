package com.example.todoapp.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.domain.model.TodoItem

@Composable
fun MainScreenContent(
    padding: PaddingValues,
    todos: List<TodoItem>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    lazyListState: LazyListState // Get LazyListState for tracking scroll position
) {
    Column(modifier = Modifier.padding(padding)) {
        // Search Bar with loading indicator
        SearchBar(query = searchQuery, onQueryChange = onQueryChange, isLoading = isLoading)

        // Error handling
        errorMessage?.let {
            ErrorState(message = it, onRetry = onRetry)
        }

        // Add gap between search bar and list content
        Spacer(modifier = Modifier.height(16.dp))

        // Todo list or empty state
        TodoListOrEmptyState(todos = todos, isLoading = isLoading, lazyListState = lazyListState)
    }
}


@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.error // Display error message in red color
            )
            Spacer(modifier = Modifier.height(16.dp)) // Add space between the message and button
            Button(onClick = onRetry) {
                Text(stringResource(R.string.retry)) // Retry button text
            }
        }
    }
}