package dev.aa55h.horaria.ui.screens.search.place

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class PlaceSearchViewModel @Inject constructor() : ViewModel() {
    val searchQuery by mutableStateOf<String?>(null)
}