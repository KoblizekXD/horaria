package dev.aa55h.horaria.ui.screens.results

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aa55h.horaria.data.model.RoutingResponse
import dev.aa55h.horaria.data.model.SearchQuery
import dev.aa55h.horaria.data.repository.TransitousRepository
import jakarta.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val transitousRepository: TransitousRepository
): ViewModel() {
    var loading by mutableStateOf(false)
    var results by mutableStateOf<RoutingResponse?>(null)

    suspend fun doSearch(searchQuery: SearchQuery) {
        loading = true
        results = transitousRepository.getRoutes(
            fromId = searchQuery.from!!.id,
            toId = searchQuery.to!!.id,
            at = searchQuery.dateTime!!
        )
        loading = false
    }
}