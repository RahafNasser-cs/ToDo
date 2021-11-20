package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.FragmentNewTaskBinding
import com.example.todo.model.TaskViewModel
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
        //To set fragment title
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.newTaskFragment)
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
        if (TaskContentIsValid()) {
            sharedViewModel.taskCreationDate()
            sharedViewModel.addNewTaskInfoTypeTask()
            findNavController().navigate(R.id.action_newTaskFragment_to_startFragment)
        }
    }

    fun pickDate() {
        sharedViewModel.showDatePicker(requireFragmentManager(), requireContext())
    }

    fun TaskContentIsValid(): Boolean {
        return if (!titleIsValid()) {
            Toast.makeText(requireContext(), getString(R.string.validate_name), Toast.LENGTH_SHORT).show()
            false
        } else if (!dateIsValid()) {
            Toast.makeText(requireContext(), getString(R.string.validate_day), Toast.LENGTH_SHORT).show()
            false
        } else if (!subtaskIsValid()) {
            Toast.makeText(requireContext(), getString(R.string.validate_subtask), Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun titleIsValid(): Boolean {
        return binding!!.taskTitleEditText.text.toString() != "" && binding!!.taskTitleEditText.text.toString().length <= 20
    }

    fun dateIsValid(): Boolean {
        return binding!!.dateEditText.text.toString() != ""
    }

    fun subtaskIsValid(): Boolean {
        return binding!!.subtaskEditText.text.toString() != ""
    }

}