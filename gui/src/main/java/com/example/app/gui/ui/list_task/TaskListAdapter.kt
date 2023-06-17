package com.example.app.gui.ui.list_task

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.app.base.gui.layoutInflater
import com.example.app.database.Task
import com.example.app.gui.R
import com.example.app.gui.databinding.ItemTaskBinding
import com.example.app.gui.getCurrentTime
import com.example.app.gui.isTimePast
import dagger.hilt.android.scopes.FragmentScoped
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * A Recycle view adapter for TaskList.
 * ${onCrossClickListener} and ${onCheckClickListener} are listeners for recycle view item
 * */

@FragmentScoped
class TaskListAdapter @Inject constructor(): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(){

    internal var taskList: List<Task> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }
    internal var onCrossClickListener: (Task) -> Unit = { _-> }
    internal var onCheckClickListener: (Task, Boolean) -> Unit = { _,_-> }

    inner class TaskViewHolder(private val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task){
            binding.apply {

                tvTaskTitle.text = task.title
                tvTaskTitle.paint.isStrikeThruText = task.taskComplete
                val taskTime = "${task.time} ${task.timeSuffix}".also {
                    tvTaskTime.text = it
                }
                val currentTime = getCurrentTime()

                if(isTimePast(currentTime, taskTime) && !task.taskComplete){
                    tvTaskTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
                    tvTaskStatus.visibility = View.VISIBLE
                }
                else{
                    tvTaskTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    tvTaskStatus.visibility = View.GONE
                }

                ibClose.setOnClickListener {
                    onCrossClickListener(task)
                }

                cbTask.isChecked = task.taskComplete

                cbTask.setOnClickListener {
                    onCheckClickListener(task, cbTask.isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskViewHolder(
        ItemTaskBinding.inflate(parent.layoutInflater, parent, false)
    )

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    // Sort taskList by task title
    internal fun sortByTitle(){
        Collections.sort(taskList, Task.sortByTitle)
        notifyItemRangeChanged(0, taskList.size)
    }

    // Sort taskList by task status
    internal fun sortByStatus(){
        Collections.sort(taskList, Task.sortByStatus)
//        notifyDataSetChanged()
        notifyItemRangeChanged(0, taskList.size)
    }

    // Sort taskList by task id/created
    internal fun sortById(){
        Collections.sort(taskList, Task.sortById)
//        notifyDataSetChanged()
        notifyItemRangeChanged(0, taskList.size)
    }

}