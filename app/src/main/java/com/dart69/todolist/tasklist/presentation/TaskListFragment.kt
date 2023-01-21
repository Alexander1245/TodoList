package com.dart69.todolist.tasklist.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentTaskListBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : BaseFragment<FragmentTaskListBinding, TaskListViewModel>(
    FragmentTaskListBinding::class, TaskListViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TaskListViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(TaskListViewPagerAdapter.ITEMS[position])
        }.attach()

        viewModel.observeScreenState().collectWithLifecycle {
            binding.toolbar.title = it.name
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}