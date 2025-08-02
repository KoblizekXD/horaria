package dev.aa55h.horaria.data.model

import dev.aa55h.horaria.R

data class SearchAutocompleteResult(
    val type: Type,
    val tokens: List<List<Int>>,
    val name: String,
    val id: String,
    val lat: String,
    val lon: String,
    val level: Int = 0,
    val street: String = "",
    val houseNumber: String = "",
    val zip: String = "",
    val areas: List<Area> = emptyList(),
    val score: Float = 0.0f,
) {
    enum class Type {
        ADDRESS,
        PLACE,
        STOP;

        fun icon() = when (this) {
            ADDRESS -> R.drawable.ic_pin_drop
            PLACE -> R.drawable.ic_location_city
            STOP -> R.drawable.ic_direction
        }
    }

    data class Area(
        val name: String,
        val adminLevel: Int,
        val matched: Boolean = false,
        val unique: Boolean = false,
        val default: Boolean = false
    )
}