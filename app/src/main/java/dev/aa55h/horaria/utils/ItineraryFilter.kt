package dev.aa55h.horaria.utils

import dev.aa55h.horaria.data.model.Itinerary

data class ItineraryFilterProperties(
    val transfers: Int,
    val duration: Long,
    val startTime: String,
    val legCount: Int
)

fun List<Itinerary>.distinct(): List<Itinerary> {
    return this.groupBy { ItineraryFilterProperties(
        it.transfers, it.duration,
        it.startTime, it.legs.size
    ) }
        .map { (_, group) -> group.first() }
}