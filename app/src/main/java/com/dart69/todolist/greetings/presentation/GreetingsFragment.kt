package com.dart69.todolist.greetings.presentation

import android.os.Bundle
import android.view.View
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentGreetingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GreetingsFragment : BaseFragment<FragmentGreetingsBinding, GreetingsViewModel>(
    FragmentGreetingsBinding::class, GreetingsViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}