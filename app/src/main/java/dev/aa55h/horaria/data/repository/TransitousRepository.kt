package dev.aa55h.horaria.data.repository

import dev.aa55h.horaria.data.remote.TransitousApiService
import jakarta.inject.Inject


class TransitousRepository @Inject constructor(
    private val api: TransitousApiService
) {
    suspend fun searchFor(query: String) = api.getGeocode(
        text = query,
        language = "en",
        type = null,
        place = null,
        placeBias = null
    )
}