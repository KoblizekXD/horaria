package dev.aa55h.horaria.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

enum class SearchScreenSource {
    FROM, TO
}

@Parcelize
@Serializable
data class SimplePlaceDefinition(
    val id: String,
    val name: String,
    val source: SearchScreenSource
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimplePlaceDefinition

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
