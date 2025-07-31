package dev.aa55h.horaria.ui.screens.search.place

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

enum class PlaceSearchSource {
    FROM,
    TO,
    GENERIC
}

@Composable
fun PlaceSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaceSearchViewModel = hiltViewModel(),
    source: PlaceSearchSource = PlaceSearchSource.GENERIC
) {

}