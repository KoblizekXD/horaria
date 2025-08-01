package dev.aa55h.horaria.ui.screens.search.place

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
class PlaceSearchViewModel @Inject constructor(
    private val transitousRepository: TransitousRepository
) : ViewModel() {
    var value by mutableStateOf("")
    var results by mutableStateOf<List<SearchAutocompleteResult>>(emptyList())

    var debounceJob: Job? = null
        private set

    fun placeSearchQueryChange(searched: String) {
        val value = searched.trim()

        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(500)
            if (value.isNotEmpty()) {
                results = transitousRepository.autocompleteSearch(value)
                Log.d("SearchViewModel", "Results for '$value': ${results.size} items")
            }
        }
    }
}