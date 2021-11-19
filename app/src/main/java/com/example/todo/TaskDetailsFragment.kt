package com.example.todo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.FragmentTaskDetailsBinding
import com.example.todo.model.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailsFragment : Fragment() {
    private var binding: FragmentTaskDetailsBinding? = null
    private val sharedViewModel: TaskViewModel by activityViewModels()
    private lateinit var popupWindow: PopupWindow
    lateinit var taskTitle: String
    lateinit var taskDate: String
    lateinit var subtask: String
    lateinit var priority: String
    lateinit var taskStatus: String
    lateinit var taskCreationDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskTitle = it.getString(TITLE).toString()
            taskDate = it.getString(DATE).toString()
            subtask = it.getString(SUBTASK).toString()
            priority = it.getString(PRIORITY).toString()
            taskStatus = it.getString(TASKSTATUS).toString()
            taskCreationDate = it.getString(CREATIONDATE).toString()
        }

        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = TaskDetailsFragmentDirections.actionTaskDetailsFragmentToStartFragment(
                sharedViewModel.title.value.toString(),
                sharedViewModel.date.value.toString(),
                sharedViewModel.subTask.value.toString(),
                sharedViewModel.priority.value.toString(),
                sharedViewModel.taskStatus.value.toString()
            )
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //To set fragment title
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.taskDetailsFragment)
        binding!!.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            taskDetailsFragment = this@TaskDetailsFragment
        }
        Log.d("TaskDetailsFragment", sharedViewModel.title.value.toString())
        sharedViewModel.title.value = taskTitle
        sharedViewModel.date.value = taskDate
        sharedViewModel.subTask.value = subtask
        sharedViewModel.priority.value = priority
        sharedViewModel.taskStatus.value = taskStatus
        sharedViewModel.creationDate.value = taskCreationDate
        binding!!.priorityContent.setBackgroundColor(
            resources.getColor(
                sharedViewModel.backgroundTintColor(
                    sharedViewModel.priority.value.toString()
                )
            )
        )
        taskIsExpiredDate()
    }

    override fun onResume() {
        super.onResume()
        updateCheckBox()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun goToNextFragment() {
    }

    fun dialogConfirmDeleteTask() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm")
            .setMessage("Are you sure to delete this task?")
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Delete") { _, _ ->
                deleteTask()
            }.show()
    }

    fun popupDeleteWindow() {
        val popupView = LayoutInflater.from(activity).inflate(R.layout.popup_delete, null)
        // Initialize a new instance of popup window
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }
        // Finally, show the popup window on app
//        TransitionManager.beginDelayedTransition(requireView())
        popupWindow.showAtLocation(
            requireView(), // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0
        )// Y offset
    }

    fun cancelDeleteTask() {
//        // Set a dismiss listener for popup window
////        popupWindow.setOnDismissListener {
////            Toast.makeText(requireContext(),"Task deleted",Toast.LENGTH_SHORT).show()
////        }
//        popupWindow.dismiss()
    }

    fun deleteTask() {
        sharedViewModel.deleteTaskFromTypeTask()
        findNavController().navigate(R.id.action_taskDetailsFragment_to_startFragment)
        Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
    }

    fun taskIsExpiredDate() {
        if (sharedViewModel.dateTimeMillis < SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS",
                Locale.UK
            ).format(Calendar.getInstance().time).toString()
        ) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Expired date!")
                .setMessage("your task date has expired")
                .setPositiveButton("Ok") { _, _ -> }.show()
        }
    }

    fun editTask() {
        sharedViewModel.testing()
        findNavController().navigate(R.id.action_taskDetailsFragment_to_editTaskFragment)
        Toast.makeText(requireContext(), "Edit task", Toast.LENGTH_SHORT).show()
    }

    fun subtaskChecked() {
        Log.d("before if else ", "before shared = ${sharedViewModel.isChecked.value!!} -- checktext = ${binding!!.subtaskContent.isChecked}")
         if (sharedViewModel.isChecked.value!!) {
             sharedViewModel.numberOfSubtaskChecked()
            binding!!.subtaskContent.isChecked = false
            Log.d("if true-->", " shared = ${sharedViewModel.isChecked.value!!} -- checktext = ${binding!!.subtaskContent.isChecked}")
        } else {
            sharedViewModel.numberOfSubtaskChecked()
            binding!!.subtaskContent.isChecked = true
            Log.d("else (false) -->", "before shared = ${sharedViewModel.isChecked.value!!} -- checktext = ${binding!!.subtaskContent.isChecked}")
        }
    }
    fun updateCheckBox() {
        Log.d("updateCheckBox","task status --> ${sharedViewModel.taskStatus.value.toString()} -- checktext --> ${binding!!.subtaskContent.isChecked} ---  isChecked --> ${sharedViewModel.isChecked.value.toString()}")
        if (sharedViewModel.taskStatus.value.toString() == "Complete") {
            binding!!.subtaskContent.isChecked = true
            if (!sharedViewModel.isChecked.value!!) {
                sharedViewModel.setIsCheck()
            }
        } else {
            binding!!.subtaskContent.isChecked = false
        }
        Log.d("updateCheckBox","task status --> ${sharedViewModel.taskStatus.value.toString()} -- checktext --> ${binding!!.subtaskContent.isChecked}  ---  isChecked --> ${sharedViewModel.isChecked.value.toString()}")
    }


    companion object {
        const val TITLE = "title"
        const val DATE = "date"
        const val SUBTASK = "subtask"
        const val PRIORITY = "priority"
        const val TASKSTATUS = "taskStatus"
        const val CREATIONDATE = "creationDate"
    }
}