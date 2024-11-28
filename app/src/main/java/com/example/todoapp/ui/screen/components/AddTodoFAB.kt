package com.example.todoapp.ui.screen.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R


@Composable
fun AddTodoFAB(onNavigateToDetails: () -> Unit) {
    FloatingActionButton(
        onClick = onNavigateToDetails,
        backgroundColor = Color(0xFF4CAF50), // Set FAB background color to green
        contentColor = Color.White // Set FAB icon color to white
    ) {
        Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_todo))
    }
}