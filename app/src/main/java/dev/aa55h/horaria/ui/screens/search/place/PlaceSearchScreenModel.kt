package dev.aa55h.horaria.ui.screens.search.place

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.aa55h.horaria.data.model.Match
import dev.aa55h.horaria.data.repository.TransitousRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaceSearchScreenModel @Inject constructor(
    val transitousRepository: TransitousRepository
): ScreenModel {
    var query by mutableStateOf("")
    val results = mutableStateListOf<Match>()

    var debounceJob: Job? = null
        private set

    fun onQueryChange(newQuery: String) {
        query = newQuery
        results.clear()
        debounceJob?.cancel()
        val searching = query.trim()
        debounceJob = screenModelScope.launch {
            delay(300)
            if (searching.isNotEmpty()) {
                results.addAll(transitousRepository.autocompleteSearch(searching))
            }
        }
    }
}