package dev.aa55h.horaria.data.model

data class Match(
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

    data class Area(
        val name: String,
        val adminLevel: Int,
        val matched: Boolean = false,
        val unique: Boolean = false,
        val default: Boolean = false
    )
}