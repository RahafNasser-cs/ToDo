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
import com.example.todo.databinding.FragmentEditTaskBinding
import com.example.todo.model.TaskViewModel

class EditTaskFragment : Fragment() {
    private var binding: FragmentEditTaskBinding? = null
    private val sharedViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //To set fragment title
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.editTaskFragment)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            editTaskFragment = this@EditTaskFragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun pickDate() {
        sharedViewModel.showDatePicker(requireFragmentManager(), requireContext())
    }

    fun goToNextFragment() {
        if (TaskContentIsValid()) {
            sharedViewModel.updateTaskInTaskType()
            val action = EditTaskFragmentDirections.actionEditTaskFragmentToTaskDetailsFragment(
                sharedViewModel.title.value.toString(),
                sharedViewModel.date.value.toString(),
                sharedViewModel.subTask.value.toString(),
                sharedViewModel.priority.value.toString(),
                sharedViewModel.taskStatus.value.toString(),
                sharedViewModel.creationDate.value.toString()
            )
            sharedViewModel.testing()
            Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
            findNavController().navigate(action)
        }
    }
    fun TaskContentIsValid(): Boolean {
        return if (!titleIsValid()) {
            Toast.makeText(requireContext(), "Enter a valid title", Toast.LENGTH_SHORT).show()
            false
        } else if (!dateIsValid()) {
            Toast.makeText(requireContext(), "Choose a date", Toast.LENGTH_SHORT).show()
            false
        } else if (!subtaskIsValid()) {
            Toast.makeText(requireContext(), "Enter a subtask", Toast.LENGTH_SHORT).show()
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