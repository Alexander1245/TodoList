package com.dart69.todolist.core.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dart69.todolist.core.domain.Identifiable

abstract class IdentifiableAdapter<T : Identifiable<*>, VH : IdentifiableViewHolder<T, *>>(
    itemCallback: ItemCallback<T> = IdentifiableItemCallback()
) : ListAdapter<T, VH>(itemCallback) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(currentList)
    }
}

abstract class IdentifiableViewHolder<T : Identifiable<*>, VB : ViewBinding>(
    protected val binding: VB
) : RecyclerView.ViewHolder(binding.root) {
    protected val List<T>.currentItem get() = this[absoluteAdapterPosition]

    abstract fun bind(items: List<T>)
}
