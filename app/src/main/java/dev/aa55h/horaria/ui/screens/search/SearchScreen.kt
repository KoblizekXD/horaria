package dev.aa55h.horaria.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SearchAutocompleteResult
import dev.aa55h.horaria.ui.components.DateChip
import dev.aa55h.horaria.ui.components.PlaceSearchBar
import dev.aa55h.horaria.ui.components.TimeChip
import dev.aa55h.horaria.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Press) {
                                    viewModel.openedBy = 0
                                    viewModel.showPlaceSearch.value = true
                                }
                            }
                        }
                    },
                placeholder = {
                    Text(text = "From...")
                },
                value = viewModel.from,
                trailingIcon = {
                    Icon(painterResource(R.drawable.ic_search), contentDescription = "Search Icon")
                },
                onValueChange = {},
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "To...")
                },
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Press) {
                                    viewModel.openedBy = 1
                                    viewModel.showPlaceSearch.value = true
                                }
                            }
                        }
                    },
                value = viewModel.to,
                trailingIcon = {
                    Icon(painterResource(R.drawable.ic_search), contentDescription = "Search Icon")
                },
                onValueChange = {},
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                DateChip(viewModel.date)
                TimeChip(viewModel.time)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Search")
            }
        }
        PlaceSearchBar(
            open = viewModel.showPlaceSearch,
            modifier = Modifier.align(Alignment.TopStart),
            onValueChange = {
                viewModel.placeSearchQueryChange(it)
            },
            results = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { isTraversalGroup = true },
                    verticalArrangement = Arrangement.Top
                ) {
                    items(viewModel.results.value) {
                        ListItem(
                            headlineContent = {
                                Text(text = it.name)
                            },
                            supportingContent = {
                                Text(
                                    text = when (it.type) {
                                        SearchAutocompleteResult.Type.ADDRESS -> buildString {
                                            append(it.street)
                                            if (it.houseNumber.isNotEmpty()) append(" ${it.houseNumber}")
                                            if (it.zip.isNotEmpty()) append(", ${it.zip}")
                                            if (it.areas.isNotEmpty()) {
                                                append(" - ")
                                                append(it.areas.joinToString(", ") { area -> area.name })
                                            }
                                        }
                                        SearchAutocompleteResult.Type.PLACE,
                                        SearchAutocompleteResult.Type.STOP -> buildString {
                                            if (it.areas.isNotEmpty()) {
                                                append(it.areas.joinToString(", ") { area -> area.name })
                                            }
                                        }
                                    }
                                )
                            },
                            modifier = Modifier.semantics { isTraversalGroup = true }
                                .clickable {
                                    if (viewModel.openedBy == 0) {
                                        viewModel.from = it.name
                                    } else if (viewModel.openedBy == 1) {
                                        viewModel.to = it.name
                                    }
                                    viewModel.openedBy = -1
                                    viewModel.results.value = emptyList()
                                    viewModel.showPlaceSearch.value = false
                                },
                            leadingContent = {
                                when (it.type) {
                                    SearchAutocompleteResult.Type.ADDRESS -> Icon(
                                        painter = painterResource(R.drawable.ic_location_city),
                                        contentDescription = "Address"
                                    )
                                    SearchAutocompleteResult.Type.PLACE -> Icon(
                                        painter = painterResource(R.drawable.ic_pin_drop),
                                        contentDescription = "Place"
                                    )
                                    SearchAutocompleteResult.Type.STOP -> Icon(
                                        painter = painterResource(R.drawable.ic_direction_bus),
                                        contentDescription = "Stop"
                                    )
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
fun SearchScreenPreview() {
    AppTheme(darkTheme = true) {
        SearchScreen(navController = rememberNavController())
    }
}