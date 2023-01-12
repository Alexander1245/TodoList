package com.dart69.todolist.splash.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::class, SplashViewModel::class
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeScreenState().collectWithLifecycle {
            binding.imageView.setImageResource(it.imageRes)
        }
        viewModel.observeEvent().collectWithLifecycle { event ->
            findNavController().also {
                it.navigate(event.directions)
            }
        }
        viewModel.showSplash()
    }
}