package com.dart69.todolist.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingClass: KClass<VB>,
    private val viewModelClass: KClass<VM>,
) : Fragment(), Screen {
    private lateinit var _binding: VB
    private lateinit var _viewModel: VM

    protected val binding: VB get() = _binding
    protected val viewModel: VM get() = _viewModel

    protected open val viewModelStoreOwner: (Fragment) -> ViewModelStoreOwner = { it }

    override fun requireLifecycleScope(): LifecycleCoroutineScope =
        viewLifecycleOwner.lifecycleScope

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewModel = ViewModelProvider(viewModelStoreOwner(this))[viewModelClass.java]
        _binding = bindingClass.members
            .filter { it.name == BINDER_NAME }
            .maxBy { it.parameters.size }
            .call(inflater, container, false) as VB
        return _binding.root
    }

    private companion object {
        const val BINDER_NAME = "inflate"
    }
}