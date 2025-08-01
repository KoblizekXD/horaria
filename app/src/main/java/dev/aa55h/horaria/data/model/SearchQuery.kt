package dev.aa55h.horaria.data.model

import dev.aa55h.horaria.ui.screens.search.SearchedAndFoundPlace
import java.io.Serializable
import java.time.LocalDateTime

data class SearchQuery(
    var from: SearchedAndFoundPlace? = null,
    var to: SearchedAndFoundPlace? = null,
    var dateTime: LocalDateTime? = null,
): Serializable
