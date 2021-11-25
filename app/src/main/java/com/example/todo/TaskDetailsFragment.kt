package com.example.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        // To set fragment title
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.taskDetailsFragment)
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
                    sharedViewModel.priority.value.toString(), requireContext()
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
            .setTitle(getString(R.string.confirm))
            .setMessage(getString(R.string.delete_msg))
            .setNegativeButton(getString(R.string.cancel_btn)) { _, _ -> }
            .setPositiveButton(getString(R.string.delete_btn)) { _, _ ->
                deleteTask()
            }.show()
    }

    fun deleteTask() {
        sharedViewModel.deleteTaskFromTypeTask()
        findNavController().navigate(R.id.action_taskDetailsFragment_to_startFragment)
        Toast.makeText(requireContext(), getString(R.string.task_deleted), Toast.LENGTH_SHORT)
            .show()
    }

    fun taskIsExpiredDate() {
        if (sharedViewModel.dateTimeMillis < SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS",
                Locale.UK
            ).format(Calendar.getInstance().time).toString()
        ) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.expired_dialog))
                .setMessage(getString(R.string.expired_date_msg))
                .setPositiveButton(getString(R.string.ok_btn)) { _, _ -> }.show()
        }
    }

    fun editTask() {
        sharedViewModel.testing()
        findNavController().navigate(R.id.action_taskDetailsFragment_to_editTaskFragment)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.editTaskFragment),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun subtaskChecked() {
        Log.d(
            "before if else ",
            "before shared = ${sharedViewModel.isChecked.value!!} -- checktext = ${binding!!.subtaskContent.isChecked}"
        )
        if (sharedViewModel.isChecked.value!!) {
            sharedViewModel.numberOfSubtaskChecked()
            binding!!.subtaskContent.isChecked = false
            Log.d(
                "if true-->",
                " shared = ${sharedViewModel.isChecked.value!!} -- checktext = ${binding!!.subtaskContent.isChecked}"
            )
        } else {
            sharedViewModel.numberOfSubtaskChecked()
            binding!!.subtaskContent.isChecked = true
            Log.d(
                "else (false) -->",
                "before shared = ${sharedViewModel.isChecked.value!!} -- checktext = ${binding!!.subtaskContent.isChecked}"
            )
        }
    }

    fun updateCheckBox() {
        Log.d(
            "updateCheckBox",
            "task status --> ${sharedViewModel.taskStatus.value} -- checktext --> ${binding!!.subtaskContent.isChecked} ---  isChecked --> ${sharedViewModel.isChecked.value}"
        )
        if (sharedViewModel.taskStatus.value.toString() == context?.resources?.getString(R.string.compelet_status)) {
            binding!!.subtaskContent.isChecked = true
            if (!sharedViewModel.isChecked.value!!) {
                sharedViewModel.setIsCheck()
            }
        } else {
            binding!!.subtaskContent.isChecked = false
        }
        Log.d(
            "updateCheckBox",
            "task status --> ${sharedViewModel.taskStatus.value} -- checktext --> ${binding!!.subtaskContent.isChecked}  ---  isChecked --> ${sharedViewModel.isChecked.value}"
        )
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
