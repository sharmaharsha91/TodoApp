package com.example.todoapp.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import kotlinx.coroutines.delay


@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, isLoading: Boolean) {
    var isProgressVisible by remember { mutableStateOf(isLoading) }

    // Introduce a 3-second delay for the progress indicator visibility
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(3000) // Show for at least 3 seconds
            isProgressVisible = false // Hide the progress indicator after 3 seconds
        } else {
            isProgressVisible = false // Hide progress immediately when loading finishes
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp) // Adds space below the AppBar
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text(stringResource(id = R.string.search_todos)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Add left and right padding (margins)
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF4CAF50), // Green color for focused border
                unfocusedBorderColor = Color(0xFF4CAF50), // Green color for unfocused border
                focusedLabelColor = Color(0xFF4CAF50), // Green label when focused
                cursorColor = Color(0xFF4CAF50) // Green cursor
            )
        )

        // Show CircularProgressIndicator when the search is loading
        if (isProgressVisible) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp) // Adjust the size of the progress indicator
                    .padding(start = 8.dp), // Space between text field and progress indicator
                strokeWidth = 2.dp
            )
        }
    }
}
