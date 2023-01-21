package com.dart69.todolist.createlist.presentation

import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
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
            findNavController().navigateUp()
        }
        viewModel.observeScreenState().collectWithLifecycle {
            binding.textInputLayout.isErrorEnabled = it is CreateListScreenState.InvalidName
            if (it is CreateListScreenState.InvalidName) {
                binding.textInputLayout.error = getString(it.message, it.argument)
            }
            binding.createButton.isEnabled = it.isCreateButtonEnabled
        }
    }
}