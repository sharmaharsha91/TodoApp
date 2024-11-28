package com.example.todoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.ui.viewmodel.TodoViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.* // Import JUnit assertions
import javax.inject.Inject

@HiltAndroidTest
class TodoViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()  // To run LiveData updates synchronously

    @Inject
    lateinit var repository: TodoRepository  // Inject the repository

    private lateinit var viewModel: TodoViewModel

    @Before
    fun setUp() {
        hiltRule.inject()  // Inject dependencies using Hilt
        viewModel = TodoViewModel(repository)  // Initialize ViewModel
    }

    @Test
    fun testAddTodo() = runTest {
        // Given: A new TODO item
        val newTodo = "Test Todo"

        // When: Call addTodo method
        viewModel.addTodo(newTodo)

        // Then: Wait for the todos list to be updated
        val currentTodos = viewModel.todos.first() // Collect the first emitted value from StateFlow

        // Assert the new todo is added to the list
        assertTrue("The new todo was not added", currentTodos.any { it.title == newTodo })
    }

    @Test
    fun testAddTodoError() = runTest {
        // Given: An error Todo item
        val errorTodo = "Error"

        // When: Call addTodo with "Error"
        viewModel.addTodo(errorTodo)

        // Then: An error message should be set in the ViewModel
        val errorMessage = viewModel.errorMessage.value
        assertEquals("Error", errorMessage)
    }

    @Test
    fun testSearchQueryUpdate() = runTest {
        // Given: A search query update
        val query = "Buy groceries"
        viewModel.updateSearchQuery(query)

        // Ensure the search query is updated correctly
        assertEquals(query, viewModel.searchQuery.value)

        // Verify the loading state is updated correctly
        assertTrue("Loading state is not correct", viewModel.isLoading.value)
    }

    @After
    fun tearDown() {
        // Reset after tests
        Dispatchers.resetMain()
    }
}
