package com.example.todo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.StartFragmentDirections
import com.example.todo.model.Task

class ItemAdapter(
    val dataset: List<Task>,
    val context: Context
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var titleTask: TextView = view.findViewById(R.id.task_title_textview)
        var priorityBtn: AppCompatButton = view.findViewById(R.id.priority_color)
        var taskCard: CardView = view.findViewById(R.id.task_card)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.titleTask.text = context.resources.getString(item.taskTitleId)
        holder.priorityBtn.setBackgroundColor(context.resources.getColor(item.priorityId))
        holder.taskCard.setOnClickListener{
            val action = StartFragmentDirections.actionStartFragmentToTaskDetailsFragment()
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = dataset.size
}