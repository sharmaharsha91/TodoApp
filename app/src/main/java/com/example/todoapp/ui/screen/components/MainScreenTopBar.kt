package com.example.todoapp.ui.screen.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R

@Composable
fun MainScreenTopBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.auto_hide_or_extend_bar), color = Color.White) }, // Set title color to white
        backgroundColor = Color(0xFF4CAF50), // Green color for AppBar
        elevation = 4.dp
    )
}