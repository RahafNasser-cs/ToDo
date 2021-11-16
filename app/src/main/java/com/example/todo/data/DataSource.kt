package com.example.todo.data

import com.example.todo.R
import com.example.todo.model.Task

class DataSource {

    fun loadTask(): List<Task> {
        return listOf(
            Task(R.string.task_title1, R.string.task_subtask1, R.color.priority_low)
            ,Task(R.string.task_title1, R.string.task_subtask1, R.color.priority_high)
            ,Task(R.string.task_title1, R.string.task_subtask1, R.color.priority_medium)
        )
    }
}