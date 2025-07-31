package dev.aa55h.horaria.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.time.LocalDate
import java.time.LocalTime

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
    var from by mutableStateOf("")
    var to by mutableStateOf("")
    var date = mutableStateOf<LocalDate?>(null)
    var time = mutableStateOf<LocalTime?>(null)
}