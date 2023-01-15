package com.dart69.todolist.core.data.mapper

import com.dart69.todolist.core.data.EntityModel
import com.dart69.todolist.core.domain.Model

fun interface ModelMapper<E: EntityModel, M: Model> {
    fun toModel(entity: E): M
}

fun interface EntityMapper<E: EntityModel, M: Model> {
    fun toEntity(model: M): E
}

interface BidirectionalMapper<E: EntityModel, M: Model>: ModelMapper<E, M>, EntityMapper<E, M>