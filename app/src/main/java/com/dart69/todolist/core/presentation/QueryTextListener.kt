package com.dart69.todolist.core.presentation

import androidx.appcompat.widget.SearchView.OnQueryTextListener

class QueryTextListener(private val searchable: Searchable) : OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let(searchable::search)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let(searchable::search)
        return true
    }
}