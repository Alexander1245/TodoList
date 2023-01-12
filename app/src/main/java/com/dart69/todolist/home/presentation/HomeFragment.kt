package com.dart69.todolist.home.presentation

import android.os.Bundle
import android.view.View
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::class, HomeViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}