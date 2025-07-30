package dev.aa55h.horaria.ui.screens.finder

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SearchAutocompleteResult
import dev.aa55h.horaria.ui.components.SearchBar
import dev.aa55h.horaria.ui.screens.search.SearchViewModel
import dev.aa55h.horaria.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinderScreen(
    navController: NavController,
    type: String,
    finderViewModel: FinderViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier.fillMaxSize()
                .focusRequester(focusRequester)
                .background(MaterialTheme.colorScheme.background),
            onValueChange = { query ->
                finderViewModel.onSearchQueryChange(query)
            },
            results = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { isTraversalGroup = true },
                    verticalArrangement = Arrangement.Top
                ) {
                    items(finderViewModel.results) {
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
                                    when (type) {
                                        "from" -> searchViewModel.from = it.name
                                        "to" -> searchViewModel.to = it.name
                                        else -> {
                                            Log.e("FinderScreen", "Unknown type: $type")
                                        }
                                    }
                                    navController.popBackStack()
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
            },
            leadingIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = "Back")
                }
            }
        )
    }
}

@Composable
@Preview
fun FinderScreenPreview() {
    AppTheme(darkTheme = true) {
        FinderScreen(rememberNavController(), type = "from")
    }
}