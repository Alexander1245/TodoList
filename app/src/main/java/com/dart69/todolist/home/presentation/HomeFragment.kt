package com.dart69.todolist.home.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dart69.todolist.R
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.core.presentation.communication.getNavigationResultLiveData
import com.dart69.todolist.createlist.presentation.CreateListDialogFragment
import com.dart69.todolist.databinding.FragmentHomeBinding
import com.dart69.todolist.task.presentation.TasksFragment
import com.dart69.todolist.task.presentation.TasksViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::class, HomeViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TaskListsAdapter(viewModel::onTaskListClick)

        setFragmentResultListener(CreateListDialogFragment.REQUEST_KEY) { _, bundle ->
            val name = bundle.getString(CreateListDialogFragment.TASK_LIST_ARG)
            name?.let(viewModel::createNewList)
        }

        setFragmentResultListener(TasksFragment.REQUEST_KEY) { _, bundle ->
            val name = bundle.getString(TasksFragment.LIST_NAME_ARG)
            name?.let(viewModel::deleteTaskList)
        }

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
            binding.buttonNewList.isEnabledWithIcon = screenState is HomeScreenState.Success

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

var MaterialButton.isEnabledWithIcon: Boolean
    get() = this.isEnabled
    set(enabled) {
        isEnabled = enabled
        icon.alpha = if(enabled) 255 else 65
    }