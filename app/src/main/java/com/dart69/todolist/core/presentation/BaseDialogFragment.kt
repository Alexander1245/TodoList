package com.dart69.todolist.core.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class BaseDialogFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingClass: KClass<VB>,
    private val viewModelClass: KClass<VM>,
) : DialogFragment(), BaseScreen {
    abstract fun initialize(binding: VB, viewModel: VM)

    override fun requireLifecycleScope(): LifecycleCoroutineScope = lifecycleScope

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val viewModel = createViewModel(viewModelClass, this)
        val binding = createBinding(bindingClass, layoutInflater, null)
        initialize(binding, viewModel)
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .show()
    }
}