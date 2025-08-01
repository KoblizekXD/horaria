package dev.aa55h.horaria.data.repository

import dev.aa55h.horaria.data.remote.TransitousApiService
import jakarta.inject.Inject
import java.time.LocalDateTime


class TransitousRepository @Inject constructor(
    private val api: TransitousApiService
) {
    suspend fun autocompleteSearch(query: String) = api.getGeocode(
        text = query,
        language = "en",
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
        language = "en",
        time = at.toString(),
    )
}