package dev.aa55h.horaria.ui.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import java.time.LocalDate
import java.time.LocalTime

@HiltViewModel
class SearchViewModel @Inject constructor(
    val transitousRepository: TransitousRepository
): ViewModel() {
    var from by mutableStateOf("")
    var to by mutableStateOf("")
    var date = mutableStateOf<LocalDate?>(null)
    var time = mutableStateOf<LocalTime?>(null)
    var showPlaceSearch = mutableStateOf(false)
    var openedBy by mutableIntStateOf(-1)

    var debounceJob: Job? = null
        private set

    val results = mutableStateOf<List<SearchAutocompleteResult>>(emptyList())

    fun placeSearchQueryChange(searched: String) {
        val value = searched.trim()

        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(500)
            if (value.isNotEmpty()) {
                results.value = transitousRepository.searchFor(value)
                Log.d("SearchViewModel", "Results for '$value': ${results.value.size} items")
            }
        }
    }
}