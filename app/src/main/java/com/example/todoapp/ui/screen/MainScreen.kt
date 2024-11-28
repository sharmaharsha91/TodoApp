package com.example.todoapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.domain.model.TodoItem
import com.example.todoapp.ui.screen.components.AddTodoFAB
import com.example.todoapp.ui.viewmodel.TodoViewModel
import com.example.todoapp.ui.screen.components.EmptyState
import com.example.todoapp.ui.screen.components.MainScreenContent
import com.example.todoapp.ui.screen.components.MainScreenTopBar
import com.example.todoapp.ui.screen.components.TodoList
import kotlinx.coroutines.delay

@Composable
fun MainScreen(viewModel: TodoViewModel, onNavigateToDetails: () -> Unit) {
    var isToolbarVisible by remember { mutableStateOf(true) }

    // Use LazyListState for managing LazyColumn scroll
    val lazyListState = rememberLazyListState()

    // Auto-hide toolbar based on scroll position
    LaunchedEffect(lazyListState.firstVisibleItemScrollOffset) {
        isToolbarVisible = lazyListState.firstVisibleItemScrollOffset == 0
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    Scaffold(
        topBar = {
            if (isToolbarVisible) {
                MainScreenTopBar()
            }
        },
        floatingActionButton = {
            AddTodoFAB(onNavigateToDetails)
        },
        content = { padding ->
            MainScreenContent(
                padding = padding,
                todos = viewModel.todos.collectAsState().value,
                searchQuery = viewModel.searchQuery.collectAsState().value,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                isLoading = viewModel.isLoading.collectAsState().value,
                errorMessage = viewModel.errorMessage.collectAsState().value,
                onRetry = { viewModel.fetchTodos() },
                lazyListState = lazyListState // Pass the LazyListState to the content
            )
        }
    )
    // Show error dialog if an error occurs
    errorMessage?.let {
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            title = { Text(stringResource(id = R.string.error)) },
            text = { Text(it) },
            confirmButton = {
                Button(onClick = { errorMessage = null }) {
                    Text(stringResource(id = R.string.ok))
                }
            }
        )
    }
}



