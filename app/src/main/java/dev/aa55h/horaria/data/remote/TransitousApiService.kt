package dev.aa55h.horaria.data.remote

import dev.aa55h.horaria.data.model.SearchAutocompleteResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TransitousApiService {
    @GET("api/v1/geocode")
    suspend fun getGeocode(
        @Query("text") text: String,
        @Query("language") language: String? = null,
        @Query("type") type: SearchAutocompleteResult.Type? = null,
        @Query("place") place: String? = null,
        @Query("placeBias") placeBias: Float? = null
    ): List<SearchAutocompleteResult>
}