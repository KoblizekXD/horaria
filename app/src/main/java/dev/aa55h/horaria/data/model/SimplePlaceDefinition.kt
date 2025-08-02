package dev.aa55h.horaria.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class SearchScreenSource {
    FROM, TO
}

@Parcelize
data class SimplePlaceDefinition(
    val id: String,
    val name: String,
    val source: SearchScreenSource
): Parcelable
