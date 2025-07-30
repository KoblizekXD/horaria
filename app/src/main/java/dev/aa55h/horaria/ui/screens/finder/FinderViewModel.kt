package dev.aa55h.horaria.ui.screens.finder

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aa55h.horaria.data.model.SearchAutocompleteResult
import dev.aa55h.horaria.data.repository.TransitousRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class FinderViewModel @Inject constructor(
    private val transitousRepository: TransitousRepository
): ViewModel() {
    var query by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var debounceJob: Job? = null
        private set

    var results by mutableStateOf(emptyList<SearchAutocompleteResult>())
        private set

    fun onSearchQueryChange(query: String) {
        this.query = query.trim()

        if (this.query.isEmpty()) {
            results = emptyList()
            return
        }

        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(500)
            try {
                isLoading = true
                results = transitousRepository.searchFor(query)
                Log.d("FinderViewModel", "Search results for \"$query\"(${results.size}): ${results.map { it.name }}")
                Log.d("FinderViewModel", "Search results: $results")
            } catch (e: Exception) {
                Log.e("FinderViewModel", "Error fetching search results", e)
                results = emptyList() // Clear results on error
            } finally {
                isLoading = false
            }
        }
    }
}