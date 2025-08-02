package dev.aa55h.horaria.data.repository

import dev.aa55h.horaria.data.remote.TransitousApiService
import dev.aa55h.horaria.ui.components.formatted
import jakarta.inject.Inject
import java.time.LocalDateTime
import java.time.ZoneOffset


class TransitousRepository @Inject constructor(
    private val api: TransitousApiService
) {
    suspend fun autocompleteSearch(query: String) = api.getGeocode(
        text = query,
        language = "cs",
        type = null,
        place = null,
        placeBias = null
    )

    suspend fun getRoutes(
        fromId: String,
        toId: String,
        at: LocalDateTime = LocalDateTime.now(),
    ) = api.getOptimalConnections(
        fromPlace = fromId,
        toPlace = toId,
        language = "cs",
        time = at.atOffset(ZoneOffset.UTC).formatted("yyyy-MM-dd'T'HH:mm:ss.SSSX"),
        numItineraries = 5
    )
}