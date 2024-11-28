package com.example.todoapp.app

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.ui.screen.DetailsScreen
import com.example.todoapp.ui.screen.MainScreen
import com.example.todoapp.ui.utils.Constants
import com.example.todoapp.ui.viewmodel.TodoViewModel
import androidx.compose.runtime.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R

@Composable
fun Navigation(navController: NavHostController) {
    // Error state to manage error messages across screens
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Navigation host
    NavHost(navController = navController, startDestination = Constants.ROUTE_MAIN) {
        // Main screen route
        composable(Constants.ROUTE_MAIN) {
            val viewModel = hiltViewModel<TodoViewModel>()
            MainScreen(
                viewModel = viewModel,
                onNavigateToDetails = { navController.navigate(Constants.ROUTE_DETAILS) }
            )

            // Display error dialog if an error occurs
            errorMessage?.let {
                AlertDialog(
                    onDismissRequest = { errorMessage = null },
                    title = { Text(stringResource(id = R.string.error)) },
                    text = { Text(it) },
                    confirmButton = {
                        Button(onClick = { errorMessage = null }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF4CAF50), // Green button
                            contentColor = Color.White // White text
                        ),) {
                            Text(stringResource(id = R.string.ok))
                        }
                    }
                )
            }
        }

        // Details screen route
        composable(Constants.ROUTE_DETAILS) {
            val viewModel = hiltViewModel<TodoViewModel>()
            DetailsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onError = { error ->
                    navController.popBackStack() // Navigate back to the main screen
                    errorMessage = error // Set the error message
                }
            )
        }
    }
}

