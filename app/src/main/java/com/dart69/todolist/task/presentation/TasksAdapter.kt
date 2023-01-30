package com.dart69.todolist.task.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableAdapter
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableViewHolder
import com.dart69.todolist.databinding.TaskItemBinding
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

    override fun bind(items: List<Task>) {
        val task = items.currentItem
        binding.textViewTaskName.text = task.name
        binding.textViewDueDate.text = task.dueDate
        binding.checkBoxIsImportant.apply {
            isChecked = task.isImportant
            setOnClickListener { callbacks.onImportantClick(task) }
        }
        binding.checkBoxIsCompleted.apply {
            isChecked = task.isCompleted
            setOnClickListener { callbacks.onCompletedClick(task) }
        }
    }

    interface Callbacks {
        fun onCompletedClick(task: Task)

        fun onImportantClick(task: Task)
    }
}