package com.dart69.todolist.createlist.presentation

import androidx.lifecycle.viewModelScope
import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.coroutines.SearcherBuilder
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.ScreenEvent
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.home.domain.NameParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CreateListEvents : ScreenEvent {
    object CloseScreen : CreateListEvents()

    data class CreateAndClose(val name: String) : CreateListEvents()
}

sealed class CreateListScreenState(
    val isCreateButtonEnabled: Boolean,
) : ScreenState {

    abstract class InvalidName : CreateListScreenState(false) {
        open val argument: Any = ""
        abstract val message: Int
    }

    data class NotUniqueName(override val argument: String) : InvalidName() {
        override val message: Int = R.string.item_already_exists
    }

    object EmptyName : InvalidName() {
        override val message: Int = R.string.name_cant_be_empty
    }

    data class VeryLongName(override val argument: Int) : InvalidName() {
        override val message: Int = R.string.very_long_name
    }

    data class CorrectName(val name: String) : CreateListScreenState(true)

    object Loading : CreateListScreenState(false)
}

@HiltViewModel
class CreateListViewModel @Inject constructor(
    private val dispatchers: AvailableDispatchers,
    private val nameParser: NameParser,
    private val screenStateFactory: CreateListScreenStateFactory,
    searcherBuilder: SearcherBuilder<Results<Boolean>, String>
) : CommunicatorViewModel<CreateListScreenState, CreateListEvents>(CreateListScreenState.EmptyName) {
    private val searcher = searcherBuilder.setDataSource(nameParser::matches).build()
    private val query = MutableStateFlow("")

    init {
        viewModelScope.launch(dispatchers.default) {
            query.collect {
                screenObserver.sendScreenState(CreateListScreenState.Loading)
            }
        }

        viewModelScope.launch(dispatchers.default) {
            searcher.observe().collect {
                screenObserver.sendScreenState(screenStateFactory.create(query.value, it))
            }
        }
    }

    fun close() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(CreateListEvents.CloseScreen)
        }
    }

    fun createNewList() {
        close()
        viewModelScope.launch(dispatchers.default) {
            val state = observeScreenState().value
            if (state is CreateListScreenState.CorrectName) {
                eventObserver.sendEvent(CreateListEvents.CreateAndClose(state.name))
            }
        }
    }

    fun onTextChanged(text: String) {
        val name = text.trim()
        query.value = name
        searcher.search(name)
    }
}