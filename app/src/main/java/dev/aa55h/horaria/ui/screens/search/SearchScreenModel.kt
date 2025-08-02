package dev.aa55h.horaria.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import dev.aa55h.horaria.data.model.SimplePlaceDefinition
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SearchScreenModel: ScreenModel {
    var from by mutableStateOf<SimplePlaceDefinition?>(null)
    var to by mutableStateOf<SimplePlaceDefinition?>(null)
    var date by mutableStateOf<LocalDate?>(null)
    var time by mutableStateOf<LocalTime?>(null)

    fun toLocalDateTime(): LocalDateTime? {
        return if (date != null && time != null) {
            LocalDateTime.of(date!!, time!!)
        } else null
    }
}