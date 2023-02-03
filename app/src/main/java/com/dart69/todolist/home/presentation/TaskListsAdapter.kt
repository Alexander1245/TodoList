package com.dart69.todolist.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableAdapter
import com.dart69.todolist.core.presentation.recyclerview.IdentifiableViewHolder
import com.dart69.todolist.databinding.SmartListItemBinding
import com.dart69.todolist.home.domain.model.TaskList

class TaskListsAdapter(
    private val onItemClick: (TaskList) -> Unit,
) : IdentifiableAdapter<TaskList, TaskListsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListsViewHolder =
        TaskListsViewHolder(
            SmartListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
}

class TaskListsViewHolder(
    binding: SmartListItemBinding,
    private val onItemClick: (TaskList) -> Unit,
) : IdentifiableViewHolder<TaskList, SmartListItemBinding>(binding) {

    override fun bind(item: TaskList) {
        binding.labelTextView.text = item.name
        binding.listIcon.setImageResource(item.icon)
        binding.root.setOnClickListener { onItemClick(item) }
    }
}