package com.dart69.todolist.createlist.presentation

import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import com.dart69.todolist.core.presentation.BaseDialogFragment
import com.dart69.todolist.databinding.FragmentCreateListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateListDialogFragment : BaseDialogFragment<FragmentCreateListBinding, CreateListViewModel>(
    FragmentCreateListBinding::class, CreateListViewModel::class
) {

    override fun initialize(binding: FragmentCreateListBinding, viewModel: CreateListViewModel) {

        binding.cancelButton.setOnClickListener {
            viewModel.close()
        }

        binding.createButton.setOnClickListener {
            viewModel.createNewList()
        }

        binding.newListNameEditText.addTextChangedListener {
            viewModel.onTextChanged(it.toString())
        }

        viewModel.observeEvent().collectWithLifecycle {
            if (it is CreateListEvents.CreateAndClose) {
                setFragmentResult(REQUEST_KEY, bundleOf(TASK_LIST_ARG to it.name))
            }
            dialog?.cancel()
        }
        viewModel.observeScreenState().collectWithLifecycle {
            binding.textInputLayout.isErrorEnabled = it is CreateListScreenState.InvalidName
            if (it is CreateListScreenState.InvalidName) {
                binding.textInputLayout.error = getString(it.message, it.argument)
            }
            binding.createButton.isEnabled = it.isCreateButtonEnabled
        }
    }

    companion object {
        const val REQUEST_KEY = "CREATE_TASK_LIST_KEY"
        const val TASK_LIST_ARG = "CREATE_TASK_LIST_ARG"
    }
}