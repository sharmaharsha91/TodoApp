package com.example.todoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.ui.utils.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)