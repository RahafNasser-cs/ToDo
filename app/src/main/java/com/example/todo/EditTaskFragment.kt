package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.data.listOfTaskTitle
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
        sharedViewModel.updateTaskInLists(sharedViewModel.findTaskIndexByTitle(sharedViewModel.title.toString(), listOfTaskTitle))
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