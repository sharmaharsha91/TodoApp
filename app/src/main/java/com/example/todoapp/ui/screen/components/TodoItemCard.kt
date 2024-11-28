package com.example.todoapp.ui.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.model.TodoItem

@Composable
fun TodoItemCard(todo: TodoItem) {
    // Your TodoItem UI card with padding, match parent width, no elevation, and no outline
    Card(
        modifier = Modifier
            .fillMaxWidth() // This makes the card fill the available width
            .padding(16.dp), // Padding around the card
        elevation = 0.dp, // Remove the elevation (shadow)
        shape = MaterialTheme.shapes.small, // Optionally use a custom shape, or RectangleShape to remove the rounded corners
        border = null // This ensures there's no border around the card
    ) {
        // Content inside the card
        Text(
            text = todo.title,
            modifier = Modifier.padding(8.dp) // Padding inside the card content
        )
    }
}