package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
        binding!!.apply {
            startFragment = this@StartFragment
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            if (listOfTaskTitle.isNotEmpty()) {
                recyclerView.adapter = ItemAdapter(DataSource().loadTask(), requireContext())
            }
            recyclerView.setHasFixedSize(true)
        }
        sharedViewModel.testing()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun goToNextFragment() {
        sharedViewModel.restart()
        findNavController().navigate(R.id.action_startFragment_to_newTaskFragment)
    }

}