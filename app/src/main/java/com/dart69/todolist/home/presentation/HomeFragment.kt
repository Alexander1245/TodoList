package com.dart69.todolist.home.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dart69.todolist.R
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::class, HomeViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TaskListsAdapter(viewModel::onTaskListClick)

        binding.recyclerViewSmartLists.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.toolbar.menu.also {
            val searchItem = it.findItem(R.id.itemSearch)
            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = getString(R.string.search_hint)
            searchView.setOnQueryTextListener(viewModel)
        }

        binding.buttonNewList.setOnClickListener { viewModel.openCreationDialog() }
        /*binding.buttonTryAgain.setOnClickListener { viewModel.tryAgain() }*/

        viewModel.observeScreenState().collectWithLifecycle { screenState ->
            binding.progressBar.isVisible = screenState is HomeScreenState.Loading
            binding.homeLayout.isVisible = screenState is HomeScreenState.Success
            binding.errorLayout.isVisible = screenState is HomeScreenState.Error
            if (screenState is HomeScreenState.Success) {
                adapter.submitList(screenState.tasks)
            }
            if (screenState is HomeScreenState.Error) {
                binding.errorTextView.setText(screenState.message)
            }
        }

        viewModel.observeEvent().collectWithLifecycle { event ->
            findNavController().navigate(event.directions)
        }
    }
}