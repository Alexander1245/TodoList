package com.dart69.todolist.taskdetails.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.activity.addCallback
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.dart69.todolist.R
import com.dart69.todolist.core.presentation.BaseFragment
import com.dart69.todolist.databinding.FragmentTaskDetailsBinding
import com.dart69.todolist.task.presentation.setText
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding, TaskDetailsViewModel>(
    FragmentTaskDetailsBinding::class, TaskDetailsViewModel::class
), DatePickerDialog.OnDateSetListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkBoxIsCompleted.setOnClickListener {
            viewModel.toggleCompleted()
        }
        binding.checkBoxIsImportant.setOnClickListener {
            viewModel.toggleImportant()
        }
        binding.editTextTaskName.addTextChangedListener {
            it?.toString()?.let(viewModel::onNameChanged)
        }
        binding.editTextNote.addTextChangedListener {
            it?.toString()?.let(viewModel::onNoteChanged)
        }
        binding.textViewDueDate.setOnClickListener {
            viewModel.openDueDateMenu()
        }
        binding.deleteIcon.setOnClickListener {
            viewModel.deleteTask()
        }
        binding.toolbar.apply {
            setNavigationOnClickListener { viewModel.editAndClose() }
        }
        viewModel.observeScreenState().collectWithLifecycle {
            val task = it.task
            binding.toolbar.title = task.listName
            binding.checkBoxIsImportant.isChecked = task.isImportant
            binding.checkBoxIsCompleted.isChecked = task.isCompleted
            task.dueDate?.let(binding.textViewDueDate::setText)
        }
        viewModel.observeEvent().collectWithLifecycle { events ->
            when (events) {
                is TaskDetailsEvents.InitializeInputs -> {
                    binding.editTextTaskName.setText(events.name)
                    binding.editTextNote.setText(events.note)
                }
                is TaskDetailsEvents.Close -> {
                    findNavController().navigateUp()
                }
                is TaskDetailsEvents.ShowPopup -> {
                    PopupMenu(requireContext(), binding.textViewDueDate).apply {
                        setOnMenuItemClickListener {
                            viewModel.onPopupMenuItemClick(it.itemId)
                            true
                        }
                        inflate(R.menu.due_date_menu)
                    }.show()
                }
                is TaskDetailsEvents.PickDate -> {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        requireContext(), this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.editAndClose()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initializeInputs()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.onDateSet(year, month, dayOfMonth)
    }
}