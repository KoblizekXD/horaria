package dev.aa55h.horaria.ui.screens.results

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aa55h.horaria.data.repository.TransitousRepository
import jakarta.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val transitousRepository: TransitousRepository
): ViewModel() {

}