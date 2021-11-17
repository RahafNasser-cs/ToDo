package com.example.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.FragmentNewTaskBinding
import com.example.todo.model.TaskViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class NewTaskFragment : Fragment() {
    private var binding: FragmentNewTaskBinding? = null
    private val sharedViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.apply {
            newTaskFragment = this@NewTaskFragment
            lifecycleOwner = viewLifecycleOwner
            viewModle = sharedViewModel
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun goToNextFragment() {
        sharedViewModel.taskCreationDate()
        sharedViewModel.addNewTaskInfo()
        findNavController().navigate(R.id.action_newTaskFragment_to_startFragment)
    }
    fun pickDate() {
        sharedViewModel.showDatePicker(requireFragmentManager(), requireContext())
    }

}