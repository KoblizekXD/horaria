package dev.aa55h.horaria.data.remote

import dev.aa55h.horaria.data.model.RoutingResponse
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

    @GET("api/v3/plan")
    suspend fun getOptimalConnections(
        @Query("fromPlace") fromPlace: String,
        @Query("toPlace") toPlace: String,
        @Query("via") via: MutableList<String?>? = null,
        @Query("viaMinimumStay") viaMinimumStay: MutableList<Int?>? = null,
        @Query("time") time: String? = null,
        @Query("maxTransfers") maxTransfers: Int? = null,
        @Query("maxTravelTime") maxTravelTime: Int? = null,
        @Query("minTransferTime") minTransferTime: Int? = null,
        @Query("additionalTransferTime") additionalTransferTime: Int? = null,
        @Query("transferTimeFactor") transferTimeFactor: Float? = null,
        @Query("maxMatchingDistance") maxMatchingDistance: Float? = null,
        @Query("pedestrianProfile") pedestrianProfile: String? = null,
        @Query("elevationCosts") elevationCosts: String? = null,
        @Query("useRoutedTransfers") useRoutedTransfers: Boolean? = null,
        @Query("detailedTransfers") detailedTransfers: Boolean = false,
        @Query("joinInterlinedLegs") joinInterlinedLegs: Boolean? = null,
        @Query("transitModes") transitModes: MutableList<String?>? = null,
        @Query("directModes") directModes: MutableList<String?>? = null,
        @Query("preTransitModes") preTransitModes: MutableList<String?>? = null,
        @Query("postTransitModes") postTransitModes: MutableList<String?>? = null,
        @Query("directRentalFormFactors") directRentalFormFactors: MutableList<String?>? = null,
        @Query("preTransitRentalFormFactors") preTransitRentalFormFactors: MutableList<String?>? = null,
        @Query("postTransitRentalFormFactors") postTransitRentalFormFactors: MutableList<String?>? = null,
        @Query("directRentalPropulsionTypes") directRentalPropulsionTypes: MutableList<String?>? = null,
        @Query("preTransitRentalPropulsionTypes") preTransitRentalPropulsionTypes: MutableList<String?>? = null,
        @Query("postTransitRentalPropulsionTypes") postTransitRentalPropulsionTypes: MutableList<String?>? = null,
        @Query("directRentalProviders") directRentalProviders: MutableList<String?>? = null,
        @Query("preTransitRentalProviders") preTransitRentalProviders: MutableList<String?>? = null,
        @Query("postTransitRentalProviders") postTransitRentalProviders: MutableList<String?>? = null,
        @Query("ignoreDirectRentalReturnConstraints") ignoreDirectRentalReturnConstraints: Boolean? = null,
        @Query("ignorePreTransitRentalReturnConstraints") ignorePreTransitRentalReturnConstraints: Boolean? = null,
        @Query("ignorePostTransitRentalReturnConstraints") ignorePostTransitRentalReturnConstraints: Boolean? = null,
        @Query("numItineraries") numItineraries: Int? = null,
        @Query("pageCursor") pageCursor: String? = null,
        @Query("timetableView") timetableView: Boolean? = null,
        @Query("arriveBy") arriveBy: Boolean? = null,
        @Query("searchWindow") searchWindow: Int? = null,
        @Query("requireBikeTransport") requireBikeTransport: Boolean? = null,
        @Query("requireCarTransport") requireCarTransport: Boolean? = null,
        @Query("maxPreTransitTime") maxPreTransitTime: Int? = null,
        @Query("maxPostTransitTime") maxPostTransitTime: Int? = null,
        @Query("maxDirectTime") maxDirectTime: Int? = null,
        @Query("fastestDirectFactor") fastestDirectFactor: Float? = null,
        @Query("timeout") timeout: Int? = null,
        @Query("passengers") passengers: Int? = null,
        @Query("luggage") luggage: Int? = null,
        @Query("slowDirect") slowDirect: Boolean? = null,
        @Query("fastestSlowDirectFactor") fastestSlowDirectFactor: Float? = null,
        @Query("withFares") withFares: Boolean? = null,
        @Query("withScheduledSkippedStops") withScheduledSkippedStops: Boolean? = null,
        @Query("language") language: String?
    ): RoutingResponse
}