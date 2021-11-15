package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todo.databinding.FragmentNewTaskBinding
import com.example.todo.model.TaskViewModel

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
        binding!!.apply { }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun goToNextFragment() {
    }
}