package com.example.todo.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.data.*
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
    private var _creationDate = MutableLiveData<String>()
    val creationDate: MutableLiveData<String>
        get() = _creationDate
    val priorityOptions = listOf("High", "Medium", "Low")
    val completionOptions = listOf("Complete", "Incomplete")
    val sdf = SimpleDateFormat("dd-MM-yyy", Locale.UK)

    private var _today = MutableLiveData<String>()
    val today: MutableLiveData<String> get() = _today


            init {
        restart()
    }
    fun restart() {
        _priority.value = priorityOptions[0]
        _date.value = ""
        _subTask.value = ""
        _title.value = ""
        _taskStatus.value = completionOptions[1]
        _today.value = currentDayMonthForAppTitle()
    }

    fun setDate(dateLong: Long) {
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

    fun showDatePicker(requireFragmentManager: FragmentManager, context: Context) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        datePicker.show(requireFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
//            if (sdf.format(it) < sdf.format( Calendar.getInstance().time)) {
////                Toast.makeText(context,"Enter a valid date", Toast.LENGTH_LONG).show()
////            }else {
                setDate(it)
//            }
        }
    }

    fun taskCreationDate() {
        val date = Calendar.getInstance().time
        _creationDate.value = sdf.format(date).toString()
        Log.d("taskCreationDate", _creationDate.value!!)
    }
    fun addNewTaskInfo() {
        listOfTaskTitle.add(_title.value!!)
        listOfTaskDate.add(_date.value!!)
        listOfSubTask.add(_subTask.value!!)
        listOfTaskPriority.add(_priority.value!!)
        listOfTaskStatus.add(_taskStatus.value!!)
        listTaskCreationDate.add(_creationDate.value!!)

    }
    fun currentDayMonthForAppTitle(): String {
        val todayName = SimpleDateFormat("EEEE").format(Calendar.getInstance().time)
        val todayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val monthName = SimpleDateFormat("MMMM").format(Calendar.getInstance().time)
        return "$todayName $todayDate $monthName"
    }
    fun deleteTaskFromLists() {

    }

    fun testing() {
        Log.d("title list", "$listOfTaskTitle")
        Log.d("date list", "$listOfTaskDate")
        Log.d("subtask list", "$listOfSubTask")
        Log.d("priority list", "${listOfTaskPriority}")
        Log.d("completion list", "${listOfTaskStatus}")
    }
}