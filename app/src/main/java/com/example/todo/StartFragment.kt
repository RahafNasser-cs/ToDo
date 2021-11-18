package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adapter.ItemAdapter
import com.example.todo.data.DataSource
import com.example.todo.data.listOfTaskTitle
import com.example.todo.databinding.FragmentStartBinding
import com.example.todo.model.TaskViewModel

class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: TaskViewModel by activityViewModels()
    lateinit var taskTitle: String
    lateinit var taskDate: String
    lateinit var subtask: String
    lateinit var priority: String
    lateinit var taskStatus: String
    lateinit var taskCreationDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskTitle = it.getString(TaskDetailsFragment.TITLE).toString()
            taskDate = it.getString(TaskDetailsFragment.DATE).toString()
            subtask = it.getString(TaskDetailsFragment.SUBTASK).toString()
            priority = it.getString(TaskDetailsFragment.PRIORITY).toString()
            taskStatus = it.getString(TaskDetailsFragment.TASKSTATUS).toString()
            taskCreationDate = it.getString(TaskDetailsFragment.CREATIONDATE).toString()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.title.value = taskTitle
        sharedViewModel.date.value = taskDate
        sharedViewModel.subTask.value = subtask
        sharedViewModel.priority.value = priority
        sharedViewModel.taskStatus.value = taskStatus
        sharedViewModel.creationDate.value = taskCreationDate
        binding!!.apply {
            startFragment = this@StartFragment
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel

        }

        sharedViewModel.testing()


    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        if (listOfTaskTitle.isNotEmpty()) {
            binding?.recyclerView?.adapter = ItemAdapter(DataSource().loadTask(), requireContext())
        }
        binding?.recyclerView?.setHasFixedSize(true)
    }
    fun goToNextFragment() {
        sharedViewModel.restart()
        findNavController().navigate(R.id.action_startFragment_to_newTaskFragment)
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