package dev.aa55h.horaria.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimplePlaceDefinition(
    val id: String,
    val name: String
): Parcelable
