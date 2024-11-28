package com.example.todoapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.model.TodoItem
import com.example.todoapp.ui.screen.DetailsScreen
import com.example.todoapp.ui.screen.MainScreen
import com.example.todoapp.ui.viewmodel.TodoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class TodoAppUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: TodoViewModel
    private lateinit var mockRepository: TodoRepository
    private val mockTodosFlow = MutableStateFlow<List<TodoItem>>(emptyList())



    @Before
    fun setUp() {
        // Mock the repository
        mockRepository = mock(TodoRepository::class.java)

        // Stub the repository's getTodos method to return the mock flow
        `when`(mockRepository.getTodos()).thenReturn(mockTodosFlow)

        // Initialize the ViewModel with the mocked repository
        viewModel = TodoViewModel(mockRepository)
    }

    @Test
    fun testAddTodoButtonNavigatesToDetailsScreen() {
        composeTestRule.setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        onNavigateToDetails = { navController.navigate("details") }
                    )
                }
                composable("details") {
                    DetailsScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onError = { }
                    )
                }
            }
        }

        // Simulate clicking the FAB button
        composeTestRule.onNodeWithContentDescription("Add TODO").performClick()

        // Assert that the DetailsScreen is displayed (Check if TextField is present)
        composeTestRule.onNodeWithText("Enter TODO").assertExists()
    }

    @Test
    fun testAddTodoItem() = runBlocking {
        composeTestRule.setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        onNavigateToDetails = { navController.navigate("details") }
                    )
                }
                composable("details") {
                    DetailsScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onError = { }
                    )
                }
            }
        }

        // Simulate clicking the FAB button
        composeTestRule.onNodeWithContentDescription("Add TODO").performClick()

        // Simulate entering a new TODO in DetailsScreen
        val newTodoTitle = "Buy milk"
        composeTestRule.onNodeWithText("Enter TODO").performTextInput(newTodoTitle)
        composeTestRule.onNodeWithText("Add TODO").performClick()

        // Simulate the repository adding the new item
        val newTodo = TodoItem(title = newTodoTitle)
        mockTodosFlow.value = listOf(newTodo)

        // Assert that the new TODO item is displayed in MainScreen
        composeTestRule.onNodeWithText("Buy milk").assertExists()
    }

    @Test
    fun testEmptyTodoListMessage() {
        composeTestRule.setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        onNavigateToDetails = { navController.navigate("details") }
                    )
                }
                composable("details") {
                    DetailsScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onError = { }
                    )
                }
            }
        }

        // Assert that the empty state message is displayed
        composeTestRule.onNodeWithText("Press the + button to add a TODO item").assertExists()
    }

    @Test
    fun testSearchFieldFiltersTodoList() = runBlocking {
        // Simulate adding TODOs to the repository
        val todos = listOf(
            TodoItem(title = "Buy groceries"),
            TodoItem(title = "Complete homework")
        )
        mockTodosFlow.value = todos

        composeTestRule.setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        onNavigateToDetails = { navController.navigate("details") }
                    )
                }
                composable("details") {
                    DetailsScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onError = { }
                    )
                }
            }
        }

        // Simulate typing into the search field
        composeTestRule.onNodeWithText("Search TODOs").performTextInput("Buy")

        // Assert that only "Buy groceries" is displayed
        composeTestRule.onNodeWithText("Buy groceries").assertExists()
        composeTestRule.onNodeWithText("Complete homework").assertDoesNotExist()
    }
}
