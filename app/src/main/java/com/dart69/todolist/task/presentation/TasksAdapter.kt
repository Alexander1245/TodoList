package com.dart69.todolist.task.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableAdapter
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableViewHolder
import com.dart69.todolist.databinding.TaskItemBinding
import com.dart69.todolist.task.domain.AvailableDates
import com.dart69.todolist.task.model.Task

class TasksAdapter(
    private val callbacks: TasksViewHolder.Callbacks
) : IdentifiableAdapter<Task, TasksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder =
        TasksViewHolder(
            TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callbacks
        )
}

class TasksViewHolder(
    binding: TaskItemBinding,
    private val callbacks: Callbacks,
) : IdentifiableViewHolder<Task, TaskItemBinding>(binding) {

    override fun bind(item: Task) {
        binding.textViewTaskName.text = item.name
        binding.textViewDueDate.setText(item.dueDate)
        binding.root.setOnClickListener {
            callbacks.onTaskClick(item)
        }
        binding.checkBoxIsImportant.apply {
            isChecked = item.isImportant
            setOnClickListener { callbacks.onImportantClick(item) }
        }
        binding.checkBoxIsCompleted.apply {
            isChecked = item.isCompleted
            setOnClickListener { callbacks.onCompletedClick(item) }
        }
    }

    interface Callbacks {
        fun onCompletedClick(task: Task)

        fun onImportantClick(task: Task)

        fun onTaskClick(task: Task)
    }
}

fun TextView.setText(date: AvailableDates?) {
    val text = if(date is AvailableDates.LabelOwner) {
        context.getString(date.label)
    } else date?.date?.toString().orEmpty()
    setText(text)
}