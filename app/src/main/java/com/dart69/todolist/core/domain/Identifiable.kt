package com.dart69.todolist.core.domain

interface Identifiable<K> {
    fun requireIdentifier(): K
}