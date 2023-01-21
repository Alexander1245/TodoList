package com.dart69.todolist.tasklist.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dart69.todolist.R
import com.dart69.todolist.completed.presentation.CompletedFragment
import com.dart69.todolist.todo.presentation.TodoFragment

class TaskListViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = ITEMS.size

    override fun createFragment(position: Int): Fragment =
        when(position) {
            0 -> TodoFragment()
            1 -> CompletedFragment()
            else -> error("Illegal position $position")
        }

    companion object {
        val ITEMS = listOf(
            R.string.to_do,
            R.string.completed,
        )
    }
}