package com.dart69.todolist.completed.presentation

import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentCompletedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedFragment : BaseFragment<FragmentCompletedBinding, CompletedViewModel>(
    FragmentCompletedBinding::class, CompletedViewModel::class
)