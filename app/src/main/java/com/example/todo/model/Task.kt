package com.example.todo.model

var currentId = 0

data class Task(
    var title: String,
    var date: String,
    var subtask: String,
    var priority: String,
    var taskStatus: String,
    var creationDate: String,
    var id: Int = currentId++
)