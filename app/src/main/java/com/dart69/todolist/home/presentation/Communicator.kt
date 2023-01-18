package com.dart69.todolist.home.presentation

import com.dart69.todolist.core.presentation.communication.Communicator
import com.dart69.todolist.home.domain.model.TaskList
import javax.inject.Inject
import javax.inject.Singleton

class TaskListCommunicator @Inject constructor(): Communicator.Default<TaskList>()