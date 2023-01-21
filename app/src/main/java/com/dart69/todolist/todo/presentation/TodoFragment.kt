package com.dart69.todolist.todo.presentation

import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentTodoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoFragment : BaseFragment<FragmentTodoBinding, TodoViewModel>(
    FragmentTodoBinding::class, TodoViewModel::class
)