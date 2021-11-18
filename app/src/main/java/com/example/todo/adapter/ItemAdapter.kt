package com.example.todo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
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
import com.example.todo.data.*
import com.example.todo.model.Task

class ItemAdapter(
    val dataset: List<Task>,
    val context: Context
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var titleTask: TextView = view.findViewById(R.id.task_title_textview)
        var priorityBtn: AppCompatButton = view.findViewById(R.id.priority_color)
        var taskCard: CardView = view.findViewById(R.id.task_card)
        var taskDate: TextView = view.findViewById(R.id.task_date_textview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (listOfTaskTitle.isNotEmpty()) {
            val title = listOfTaskTitle[position]
            val date = listOfTaskDate[position]
            val subtask = listOfSubTask[position]
            val taskStatus = listOfTaskStatus[position]
            val priority = when(listOfTaskPriority[position]) {
                "High"-> {
                    R.color.priority_high
                }
                "Medium"-> {
                    R.color.priority_medium
                }
                else-> {
                    R.color.priority_low
                }
            }

            if (taskStatus == "Complete") {
                Log.d("In Adapter (if--> complete)","taskstatus $taskStatus")
                val ss = SpannableString(title)
                val strikeThrowSpan = StrikethroughSpan()
                ss.setSpan(strikeThrowSpan,0, title.length-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                holder.titleTask.text = ss
//                holder.titleTask.paintFlags = holder.titleTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                holder.titleTask.setTextAppearance(R.style.italicText)
            }else {
                Log.d("In Adapter (else--> incomplete)","taskstatus $taskStatus")
                holder.titleTask.text = title
            }
            holder.taskDate.text = date
            holder.priorityBtn.setBackgroundColor(context.resources.getColor(priority))
            holder.taskCard.setOnClickListener{
                Log.d("on click card", "title = $title")
                Log.d("on click card", date)
                val action = StartFragmentDirections.actionStartFragmentToTaskDetailsFragment(title,date,subtask,
                    listOfTaskPriority[position],taskStatus, listTaskCreationDate[position])
                holder.view.findNavController().navigate(action)
            }

//            val item = dataset[position]
//            holder.titleTask.text = context.resources.getString(item.taskTitleId)
//            holder.priorityBtn.setBackgroundColor(context.resources.getColor(item.priorityId))
//            holder.taskCard.setOnClickListener{
//                val action = StartFragmentDirections.actionStartFragmentToTaskDetailsFragment()
//                holder.view.findNavController().navigate(action)
//            }
        }
    }

    override fun getItemCount(): Int = listOfTaskTitle.size
}