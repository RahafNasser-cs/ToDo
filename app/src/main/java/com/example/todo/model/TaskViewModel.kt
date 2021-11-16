package com.example.todo.model

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class TaskViewModel : ViewModel() {

    private var _date = MutableLiveData<String>()
    val date: MutableLiveData<String>
        get() = _date
    private var _title = MutableLiveData<String>()
    val title: MutableLiveData<String>
        get() = _title
    private var _priority = MutableLiveData<String>()
    val priority: MutableLiveData<String>
        get() = _priority
    private var _subTask = MutableLiveData<String>()
    val subTask: MutableLiveData<String>
        get() = _subTask
    private var _taskStatus = MutableLiveData<String>()
    val taskStatus: MutableLiveData<String>
        get() = _taskStatus
    val priorityOptions = listOf("High", "Medium", "Low")
    val completionOptions = listOf("Complete", "Incomplete")


    //    init {
//        restart()
//    }
    fun restart() {
        _priority.value = priorityOptions[0]
        _date.value = ""
        _subTask.value = ""
    }

    fun setDate(dateLong: Long) {
        val sdf = SimpleDateFormat("dd-MM-yyy", Locale.UK)
        val date = Date(Timestamp(dateLong).time)
        _date.value = sdf.format(date).toString()
        Log.d("setDate", _date.value!!)
    }

    fun setTitle(title: String) {
        _title.value = title
        Log.d("setTitle", _title.value!!)
    }

    fun setSubtask(subtask: String) {
        _subTask.value = subtask
        Log.d("setSubtask", _subTask.value!!)
    }

    fun setPriority(priority: String) {
        _priority.value = priority
        Log.d("setPriority", _priority.value!!)
    }

    fun setTaskStatusComplete(taskStatus: String) {
        _taskStatus.value = taskStatus
    }
//    fun addListOfSubtask(subtask: String) {
//        _subTaskList.add(subtask)
//        _subTask.value = ""
//        //subTaskList = _subTaskList.joinToString("\n")
//    }

    fun showDatePicker(requireFragmentManager: FragmentManager) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        datePicker.show(requireFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
            setDate(it)
        }
    }
//    fun addSubtask() {
//
//    }

    fun texting() {
        Log.d("title", "${_title.value}")
        Log.d("date", "${_date.value}")
        Log.d("subtask", "${_subTask.value}")
        Log.d("priority", "${_priority.value}")
        Log.d("completion", "${_taskStatus.value}")
    }
}