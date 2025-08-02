package dev.aa55h.horaria.ui.screens.search.results

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.aa55h.horaria.data.model.RoutingResponse
import dev.aa55h.horaria.data.repository.TransitousRepository
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SearchResultScreenModel @Inject constructor(
    private val transitousRepository: TransitousRepository
): ScreenModel {
    var loading: Boolean by mutableStateOf(true)
        private set
    var results: RoutingResponse? by mutableStateOf(null)

    fun findRoutes(
        from: String,
        to: String,
        at: LocalDateTime
    ) = screenModelScope.launch {
        loading = true
        results = transitousRepository.getRoutes(
            fromId = from,
            toId = to,
            at = at
        )
        loading = false
    }
}