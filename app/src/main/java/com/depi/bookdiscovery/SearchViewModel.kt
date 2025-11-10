package com.depi.bookdiscovery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depi.bookdiscovery.util.SettingsDataStore
import com.depi.bookdiscovery.dto.Item
import com.depi.bookdiscovery.repo.Repo
import com.depi.bookdiscovery.repo.RepoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: RepoService = Repo(),
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Item>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    val searchHistory = settingsDataStore.searchHistory

    private var searchJob: Job? = null
    private var currentQuery: String = ""
    private var startIndex = 0
    private val maxResults = 20

    fun search(query: String, filter: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(2000) // Debounce time of 2 seconds
            if (query.isNotEmpty()) {
                currentQuery = "$query+printType:$filter"
                startIndex = 0
                _searchResults.value = emptyList()
                fetchBooks()
                settingsDataStore.addToSearchHistory(query)
            }
        }
    }

    fun searchNow(query: String, filter: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                currentQuery = "$query+printType:$filter"
                startIndex = 0
                _searchResults.value = emptyList()
                fetchBooks()
                settingsDataStore.addToSearchHistory(query)
            }
        }
    }

    fun loadMore() {
        if (searchJob?.isActive == true) return
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            fetchBooks()
        }
    }

    private suspend fun fetchBooks() {
        val response = repo.searchBooks(currentQuery, maxResults, startIndex)
        if (response.isSuccessful) {
            val newBooks = response.body()?.items ?: emptyList()
            _searchResults.value = _searchResults.value + newBooks
            startIndex += newBooks.size
        } else {
            Log.w("SearchViewModel", "Something went wrong: ${response.errorBody()?.string()}")
        }
    }
}