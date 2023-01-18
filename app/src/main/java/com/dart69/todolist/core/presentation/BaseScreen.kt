package com.dart69.todolist.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

interface BaseScreen : Screen {
    fun <T : ViewModel> createViewModel(
        viewModelClass: KClass<T>,
        storeOwner: ViewModelStoreOwner
    ): T = ViewModelProvider(storeOwner)[viewModelClass.java]

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewBinding> createBinding(
        bindingClass: KClass<T>,
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T = bindingClass.members
        .filter { it.name == BINDER_NAME }
        .maxBy { it.parameters.size }
        .call(inflater, container, false) as T

    companion object {
        const val BINDER_NAME = "inflate"
    }
}