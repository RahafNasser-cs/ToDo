package com.example.todo.data

import android.util.Log
import com.example.todo.model.Task

class DataSource {
    val listOfTasks = mutableListOf<Task>()

    fun loadTask(): List<Task> {
        return listOfTasks
    }

    fun loadFilterTaskPriority(filterTag: String = ""): List<Task> {
        if (filterTag.isEmpty()) {
            return listOfTasks
        } else {
            return listOfTasks.filter { it.priority == filterTag }
        }
    }

    fun loadFilterTaskDate(filterTag: String = ""): List<Task> {
        if (filterTag.isEmpty()) { // filter based on creation date
            return loadTask()
        } else { // filter based on deadline
            var listDatesort = listOfTasks
            listDatesort.sortBy { it.date }
            Log.d("loadFilterTaskDate", "$listDatesort")
            return listDatesort
        }
    }

    fun loadFilterTaskDeadline(): List<Task> {
        var listDatesort = listOfTasks.toList().toMutableList()
        listDatesort.sortBy { it.date }
        Log.d("loadFilterTaskDate", "$listDatesort")
        return listDatesort
    }

    fun addTask(
        title: String,
        date: String,
        subtask: String,
        priority: String,
        taskStatus: String,
        creationDate: String
    ) {
        listOfTasks.add(Task(title, date, subtask, priority, taskStatus, creationDate))
    }

    fun deleteTask(id: Int) {
        var index = listOfTasks.indexOfFirst { it.id == id }
        listOfTasks.removeAt(index)
    }

    fun updateTask(
        title: String,
        date: String,
        subtask: String,
        priority: String,
        taskStatus: String,
        creationDate: String,
        id: Int
    ) {
        var index = listOfTasks.indexOfFirst { it.id == id }
        listOfTasks[index].title = title
        listOfTasks[index].date = date
        listOfTasks[index].subtask = subtask
        listOfTasks[index].priority = priority
        listOfTasks[index].taskStatus = taskStatus
        listOfTasks[index].creationDate = creationDate
    }
}
