package dev.aa55h.horaria.ui.screens.results

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aa55h.horaria.data.model.RoutingResponse
import dev.aa55h.horaria.data.model.SearchQuery
import dev.aa55h.horaria.data.repository.TransitousRepository
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val transitousRepository: TransitousRepository
): ViewModel() {
    var loading by mutableStateOf(false)
    var results by mutableStateOf<RoutingResponse?>(null)

    fun doSearch(searchQuery: SearchQuery) = viewModelScope.launch {
        loading = true
        results = transitousRepository.getRoutes(
            fromId = searchQuery.from!!.id,
            toId = searchQuery.to!!.id,
            at = LocalDateTime.parse(searchQuery.dateTime)
        )
        loading = false
    }
}