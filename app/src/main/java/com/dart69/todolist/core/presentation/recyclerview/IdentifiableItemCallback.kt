package com.dart69.todolist.core.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.dart69.todolist.core.domain.Identifiable

class IdentifiableItemCallback<T : Identifiable<*>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.requireIdentifier() == newItem.requireIdentifier()

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.equals(newItem)
}