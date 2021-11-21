package com.example.todo.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.R
import com.example.todo.data.*
import com.google.android.material.datepicker.MaterialDatePicker
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class TaskViewModel : ViewModel() {

    private var _date = MutableLiveData<String>()
    val date: MutableLiveData<String>
        get() = _date
    private var _dateTimeMillis = ""
    val dateTimeMillis: String get() = _dateTimeMillis
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

    private var _isChecked = MutableLiveData<Boolean>()
    val isChecked: MutableLiveData<Boolean>
        get() = _isChecked

    private var _today = MutableLiveData<String>()
    val today: MutableLiveData<String> get() = _today
    val priorityOptions = mutableListOf<String>()
    var completionOptions = mutableListOf<String>()
    val sdf = SimpleDateFormat("dd-MM-yyy", Locale.UK)
    private var numberOfTaskChecked = 0

    var dataset = DataSource()
    private var index = -1


    init {
        restart()
    }

    fun getContextForTaskStatus(fragmentContext: Context){
        completionOptions.addAll(listOf(fragmentContext.getString(R.string.compelet_status), fragmentContext.getString(
            R.string.incomplete_status)))
        _taskStatus.value = completionOptions[1]
    }
    fun getContextForTaskPriority(fragmentContext: Context){
        priorityOptions.addAll(listOf(fragmentContext.getString(R.string.high_priority), fragmentContext.getString(
            R.string.medium_priority), fragmentContext.getString(
            R.string.low_priority)))
        _priority.value = priorityOptions[0]

    }

    fun restart() {
        _priority.value = ""
        _date.value = ""
        _subTask.value = ""
        _title.value = ""
        _taskStatus.value = ""
        _today.value = currentDayMonthForAppTitle()
        _isChecked.value = false
    }

    fun setDate(dateLong: Long) {
        val date = Date(Timestamp(dateLong).time)
        _date.value = sdf.format(date).toString()
        Log.d("setDate", _date.value!!)

    }

    fun convertToTimeMillis(date: Long) {
        var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        Log.d("setDate milisec", sdf.format(date))
        _dateTimeMillis = sdf.format(date)
    }

    fun setIsCheck() {
        _isChecked.value = !_isChecked.value!!
    }

    fun setPriority(priority: String) {
        _priority.value = priority
        Log.d("setPriority", _priority.value!!)
    }

    fun setTaskStatusComplete(taskStatus: String) {
        _taskStatus.value = taskStatus
    }

    fun showDatePicker(requireFragmentManager: FragmentManager, context: Context) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        datePicker.show(requireFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
            if (sdf.format(it) < sdf.format(Calendar.getInstance().time)) {
                Toast.makeText(
                    context,
                    context.getString(R.string.enter_valid_date),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                setDate(it)
                convertToTimeMillis(it)
            }
        }
    }

    fun taskCreationDate() {
        val date = Calendar.getInstance().time
        _creationDate.value = sdf.format(date).toString()
        Log.d("taskCreationDate", _creationDate.value!!)
    }

    fun currentDayMonthForAppTitle(): String {
        val todayName = SimpleDateFormat("EEEE").format(Calendar.getInstance().time)
        val todayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val monthName = SimpleDateFormat("MMMM").format(Calendar.getInstance().time)
        return "$todayName $todayDate $monthName"
    }

    fun addNewTaskInfoTypeTask() {
        dataset.addTask(
            _title.value!!,
            _date.value!!,
            _subTask.value!!,
            _priority.value!!,
            _taskStatus.value!!,
            _creationDate.value!!
        )
        index++
    }

    fun deleteTaskFromTypeTask() {
        dataset.deleteTask(index)
    }

    fun updateTaskInTaskType() {
        dataset.updateTask(
            _title.value!!,
            _date.value!!,
            _subTask.value!!,
            _priority.value!!,
            _taskStatus.value!!,
            _creationDate.value!!,
            index
        )
    }

    fun testing() {
        Log.d("dataset-->", "${dataset}")
    }

    //To set priority color
    fun backgroundTintColor(priority: String, context: Context): Int {
        return when (priority) {
            context.resources.getString(R.string.high_priority) -> {
                R.color.priority_high
            }
            context.resources.getString(R.string.medium_priority) -> {
                R.color.priority_medium
            }
            else -> {
                R.color.priority_low
            }
        }
    }

    //To count number of subtask is checked
    fun numberOfSubtaskChecked() {
//        numberOfTaskChecked++
        _isChecked.value = !isChecked.value!!
        if (_isChecked.value!!) {
            _taskStatus.value = completionOptions[0]
            dataset.listOfTasks[index].taskStatus = completionOptions[0]
        } else {
            _taskStatus.value = completionOptions[1]
            dataset.listOfTasks[index].taskStatus = completionOptions[1]

        }
    }

}