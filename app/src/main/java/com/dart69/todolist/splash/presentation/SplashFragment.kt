package com.dart69.todolist.splash.presentation

import android.os.Bundle
import android.view.View
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::class, SplashViewModel::class
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeScreenState().collectWithLifecycle {
            binding.imageView.setImageResource(it.imageRes)
        }
    }
}