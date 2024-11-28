package com.example.todoapp.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    val todos: StateFlow<List<TodoItem>> = repository.getTodos()
        .combine(_searchQuery) { todos, query ->
            if (query.isEmpty()) todos else todos.filter { it.title.contains(query, true) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTodo(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.addTodo(TodoItem(title = title))
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add TODO. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

//    fun updateSearchQuery(query: String) {
//        _searchQuery.value = query
//    }

    // Function to handle search query updates with debounce logic
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        debounceSearch(query)
    }

    private fun debounceSearch(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(300) // Debounce delay of 300ms
            if (_searchQuery.value == query) {
                // Trigger the search only if the query hasn't changed
                performSearch(query)
            }
        }
    }

    private fun performSearch(query: String) {
        // Simulate a search operation here
        // For example, filter todos based on the query
        viewModelScope.launch {
            _isLoading.value = false // Set isLoading to false after the search is done
        }
    }

    fun fetchTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch todos logic here
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch TODOs. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}




