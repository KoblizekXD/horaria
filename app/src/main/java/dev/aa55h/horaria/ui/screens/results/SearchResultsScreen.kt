package dev.aa55h.horaria.ui.screens.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aa55h.horaria.data.model.SearchQuery
import dev.aa55h.horaria.ui.components.ItineraryCard

@Composable
fun SearchResultsScreen(
    searchResultsViewModel: SearchResultsViewModel = hiltViewModel(),
    searchQuery: SearchQuery
) {
    LaunchedEffect(Unit) {
        searchResultsViewModel.doSearch(searchQuery)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (searchResultsViewModel.loading) {
            Text("Searching...", style = MaterialTheme.typography.titleLarge)
            LinearProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(searchResultsViewModel.results!!.itineraries) {
                    ItineraryCard(it)
                }
            }
        }
    }
}