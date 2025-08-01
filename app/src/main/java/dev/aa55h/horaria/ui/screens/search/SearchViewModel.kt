package dev.aa55h.horaria.ui.screens.search

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
@Parcelize
data class SearchedAndFoundPlace(
    val name: String,
    val id: String
): Parcelable

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
    var from by mutableStateOf<SearchedAndFoundPlace?>(null)
    var to by mutableStateOf<SearchedAndFoundPlace?>(null)
    var date = mutableStateOf<LocalDate?>(null)
    var time = mutableStateOf<LocalTime?>(null)
}