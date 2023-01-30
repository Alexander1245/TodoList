package com.dart69.todolist.task.presentation

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dart69.todolist.R
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : BaseFragment<FragmentTasksBinding, TasksViewModel>(
    FragmentTasksBinding::class, TasksViewModel::class
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TasksAdapter(viewModel)
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())

            val divider = DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            ).also {
                it.setDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.recyclerview_divider)!!
                )
            }
            addItemDecoration(divider)

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.deleteTask(viewHolder.absoluteAdapterPosition)
                }
            }).attachToRecyclerView(this)
        }

        viewModel.observeScreenState().collectWithLifecycle {
            binding.toolbar.title = it.listName
            binding.inputLayout.isVisible = it.isInputVisible
            binding.buttonAddTask.isVisible = it.isButtonVisible
            binding.progressLayout.root.isVisible = it.isProgressVisible
            binding.toolbar.menu.setGroupVisible(R.id.modificationsGroup, it.isModificationsAllowed)
            binding.editTextTaskName.isEnabled = it.isInputEnabled
            adapter.submitList(it.tasks)
            if (it.isInputVisible) {
                binding.editTextTaskName.toggleFocus()
            }
        }

        viewModel.observeEvent().collectWithLifecycle {
            when (it) {
                is TasksEvents.DeleteTaskList -> {
                    setFragmentResult(REQUEST_KEY, bundleOf(LIST_NAME_ARG to it.name))
                    findNavController().navigateUp()
                }
                is TasksEvents.ClearTextInput -> binding.editTextTaskName.text?.clear()
            }
        }

        binding.checkBoxIsCompleted.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleNewTaskCompleted(isChecked)
        }

        binding.toolbar.apply {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemDelete -> viewModel.deleteTaskList()
                    R.id.itemCompleted -> viewModel.filterByCompleted()
                    R.id.itemToDo -> viewModel.filterByToDo()
                }
                true
            }
        }

        binding.buttonAddTask.setOnClickListener {
            viewModel.toggleTextInput()
        }

        binding.editTextTaskName.onSubmit {
            viewModel.createNewTask(binding.editTextTaskName.text.toString())
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            //TODO: Place in viewModel
            if (viewModel.observeScreenState().value.isInputVisible) {
                viewModel.toggleTextInput()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "DELETE_TASK_LIST_KEY"
        const val LIST_NAME_ARG = "DELETE_LIST_NAME_ARG"
    }
}

fun EditText.toggleFocus() {
    requestFocus()
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

fun EditText.onSubmit(block: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            block()
        }
        true
    }
}