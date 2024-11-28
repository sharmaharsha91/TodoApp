package com.example.todoapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.viewmodel.TodoViewModel
import kotlinx.coroutines.delay

@Composable
fun DetailsScreen(viewModel: TodoViewModel, onBack: () -> Unit, onError: (String) -> Unit) {
    var input by remember { mutableStateOf("") }
    var isAddingTodo by remember { mutableStateOf(false) }
    val failedToAddTodoMessage = stringResource(id = R.string.failed_to_add_todo)
    // Handle navigation after adding TODO
    LaunchedEffect(isAddingTodo) {
        if (isAddingTodo) {
            delay(3000) // Show progress indicator for 3 seconds
            isAddingTodo = false
            onBack() // Navigate back to the main screen
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.add_todo), color = Color.White) },
                backgroundColor = Color(0xFF4CAF50), // Green toolbar
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back), tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // TextField for entering TODO
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text(stringResource(id = R.string.enter_todo)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4CAF50), // Green color for focused border
                    unfocusedBorderColor = Color(0xFF4CAF50), // Green color for unfocused border
                    focusedLabelColor = Color(0xFF4CAF50), // Green label when focused
                    cursorColor = Color(0xFF4CAF50) // Green cursor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add TODO button
            Button(
                onClick = {
                    // Show loading indicator
                    if (input.equals("Error", ignoreCase = true)) {
                        onError(failedToAddTodoMessage)
                    } else {
                        viewModel.addTodo(input)
                        isAddingTodo = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4CAF50), // Green button
                    contentColor = Color.White // White text
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.add_todo))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Show Circular Progress Indicator while adding TODO
            if (isAddingTodo) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color(0xFF4CAF50), // Green circular indicator
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

