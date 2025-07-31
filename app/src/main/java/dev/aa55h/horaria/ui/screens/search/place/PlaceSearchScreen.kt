package dev.aa55h.horaria.ui.screens.search.place

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SearchAutocompleteResult

enum class PlaceSearchSource {
    FROM,
    TO,
    GENERIC
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceSearchScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: PlaceSearchViewModel = hiltViewModel(),
    source: PlaceSearchSource = PlaceSearchSource.GENERIC
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    fun handleBackPress(result: String) {
        when (source) {
            PlaceSearchSource.FROM -> navController.previousBackStackEntry?.savedStateHandle?.set("from", result)
            PlaceSearchSource.TO -> navController.previousBackStackEntry?.savedStateHandle?.set("to", result)
            PlaceSearchSource.GENERIC -> {}
        }
        navController.popBackStack()
    }

    SearchBar(
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(WindowInsets(0.dp, 0.dp, 0.dp, 0.dp))
            .wrapContentHeight(Alignment.Top)
            .then(modifier),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.focusRequester(focusRequester),
                query = viewModel.value,
                onQueryChange = {
                    viewModel.value = it
                    viewModel.placeSearchQueryChange(it)
                },
                onSearch = {},
                leadingIcon = {
                    IconButton({
                        navController.popBackStack()
                    }) {
                        Icon(painterResource(R.drawable.ic_arrow_back), "Back")
                    }
                },
                expanded = true,
                onExpandedChange = { },
                placeholder = { Text("Search...") },
            )
        },
        expanded = true,
        onExpandedChange = {}
    ) {
        viewModel.results.map {
            ListItem(
                modifier = Modifier.semantics { isTraversalGroup = true }
                    .clickable {
                        handleBackPress(it.name)
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
                },
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
                                it.areas.subList(0, 1).forEachIndexed { index, area ->
                                    append(area.name)
                                    if (index < it.areas.size - 1) append(", ")
                                }
                            }
                            SearchAutocompleteResult.Type.PLACE,
                            SearchAutocompleteResult.Type.STOP -> buildString {
                                it.areas.subList(0, 3).forEachIndexed { index, area ->
                                    append(area.name)
                                    if (index < 2) append(", ")
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}