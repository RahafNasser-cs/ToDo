package com.example.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.adapter.ItemAdapter
import com.example.todo.databinding.FragmentStartBinding
import com.example.todo.model.Task
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
    var listOfFilteredTask = listOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        //To set fragment title
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.startFragment)
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
        if (sharedViewModel.dataset.loadTask().isNotEmpty()) {
            binding?.recyclerView?.adapter = ItemAdapter(sharedViewModel.dataset.loadTask(), requireContext())
        }
        binding?.recyclerView?.setHasFixedSize(true)
    }
    fun goToNextFragment() {
        sharedViewModel.restart()
        findNavController().navigate(R.id.action_startFragment_to_newTaskFragment)
    }
    fun filterTaskPriority(filterTag: String="") {
        Log.d("filterTaskPriority()", "flage = ${sharedViewModel.dataset.loadFilterTaskPriority(filterTag)}")
        listOfFilteredTask=sharedViewModel.dataset.loadFilterTaskPriority(filterTag)
        binding?.recyclerView?.adapter=ItemAdapter(listOfFilteredTask,this.requireContext())
    }
    fun filterTaskDate(filterTag: String = "") {
        listOfFilteredTask = sharedViewModel.dataset.loadFilterTaskDeadline()
        binding?.recyclerView?.adapter=ItemAdapter(listOfFilteredTask,this.requireContext())
    }
    fun filteredTask(filterTag: String) {
        Log.d("filteredTask()", "flage = ${sharedViewModel.dataset.loadFilterTaskPriority("High")}")
        listOfFilteredTask=sharedViewModel.dataset.loadFilterTaskPriority(filterTag)
        binding?.recyclerView?.adapter=ItemAdapter(listOfFilteredTask,this.requireContext())
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.high_priority -> {
                filterTaskPriority("High")
            }
            R.id.medium_priority -> {
                filterTaskPriority("Medium")
            }
            R.id.low_priority -> {
                filterTaskPriority("Low")
            }
            R.id.no_filter -> {
                filterTaskPriority()
            }
            R.id.deadline_filter -> {
                filterTaskDate()
            }
//            R.id.creation_date -> {
//                filterTaskDate()
//            }

        }
        return true
    }
}