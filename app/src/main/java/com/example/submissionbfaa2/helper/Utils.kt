package com.example.submissionbfaa2.helper

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.submissionbfaa2.R

fun Activity.createSearchViewMenu(menu: Menu?, listener: (String) -> Unit) {
    menuInflater.inflate(R.menu.option_menu, menu)

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
    searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
    searchView.queryHint = resources.getString(R.string.search_hint)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            query?.let { listener.invoke(it) }
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    })
}