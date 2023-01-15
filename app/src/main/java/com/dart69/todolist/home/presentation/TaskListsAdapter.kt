package com.dart69.todolist.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableAdapter
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableViewHolder
import com.dart69.todolist.databinding.SmartListItemBinding
import com.dart69.todolist.home.domain.model.TaskList

class TaskListsAdapter :
    IdentifiableAdapter<TaskList, TaskListsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListsViewHolder =
        TaskListsViewHolder(SmartListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

class TaskListsViewHolder(
    binding: SmartListItemBinding
) : IdentifiableViewHolder<TaskList, SmartListItemBinding>(binding) {

    override fun bind(items: List<TaskList>) {
        val taskList = items.currentItem
        binding.labelTextView.text = taskList.name
        binding.listIcon.setImageResource(taskList.icon)
    }
}