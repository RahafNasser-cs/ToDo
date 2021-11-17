package com.example.todo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import com.example.todo.databinding.FragmentTaskDetailsBinding
import com.example.todo.model.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailsFragment : Fragment() {
    private var binding: FragmentTaskDetailsBinding? = null
    private val sharedViewModel: TaskViewModel by activityViewModels()
    private lateinit var popupWindow: PopupWindow

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            taskDetailsFragment = this@TaskDetailsFragment

        }
        taskIsExpiredDate()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun goToNextFragment() {
    }

    fun dialogConfirmDeleteTask() {
        Toast.makeText(requireContext(), "Task not deleted", Toast.LENGTH_LONG).show()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm")
            .setMessage("Are you sure to delete this task?")
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(requireContext(), "Task not deleted", Toast.LENGTH_LONG).show()
            }
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
        //sharedViewModel.deleteTaskFromLists()
        Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
    }

    fun taskIsExpiredDate() {
        if (sharedViewModel.date.value!!.toString() < SimpleDateFormat(
                "dd-MM-yyy",
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
        Toast.makeText(requireContext(), "Edit task", Toast.LENGTH_SHORT).show()
    }
}