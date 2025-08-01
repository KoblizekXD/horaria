package dev.aa55h.horaria.data.model

import android.os.Parcelable
import dev.aa55h.horaria.ui.screens.search.SearchedAndFoundPlace
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchQuery(
    var from: SearchedAndFoundPlace? = null,
    var to: SearchedAndFoundPlace? = null,
    var dateTime: String? = null,
): Parcelable
