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
) : Fragment(), BaseScreen {
    protected lateinit var binding: VB
        private set

    protected lateinit var viewModel: VM
        private set

    override fun requireLifecycleScope(): LifecycleCoroutineScope =
        viewLifecycleOwner.lifecycleScope

    protected open fun requireStoreOwner(): ViewModelStoreOwner = this

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = createViewModel(viewModelClass, requireStoreOwner())
        binding = createBinding(bindingClass, inflater, container)
        return binding.root
    }
}