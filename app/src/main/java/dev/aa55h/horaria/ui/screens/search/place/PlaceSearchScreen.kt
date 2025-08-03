package dev.aa55h.horaria.ui.screens.search.place

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SearchScreenSource
import dev.aa55h.horaria.data.model.SimplePlaceDefinition
import dev.aa55h.horaria.data.model.Type
import dev.aa55h.horaria.utils.VoyagerResultExtension
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class PlaceSearchScreen(
    private val type: SearchScreenSource,
    private val navigatorExtension: VoyagerResultExtension
): Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<PlaceSearchScreenModel>()
        val focusRequester = remember { FocusRequester() }
        val navigator = LocalNavigator.currentOrThrow
        val recentSearches by screenModel.recentSearchesRepository.recentSearches.collectAsState(initial = emptySet())
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        SearchBar(
            windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            modifier = Modifier.consumeWindowInsets(WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)),
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            expanded = true,
            inputField = {
                SearchBarDefaults.InputField(
                    modifier = Modifier.focusRequester(focusRequester),
                    query = screenModel.query,
                    onQueryChange = { screenModel.onQueryChange(it) },
                    leadingIcon = {
                        IconButton({
                            navigator.pop()
                        }) {
                            Icon(painterResource(R.drawable.ic_arrow_back), "Back")
                        }
                    },
                    trailingIcon = {
                        if (screenModel.query.isNotEmpty()) {
                            IconButton({
                                screenModel.onQueryChange("")
                            }) {
                                Icon(painterResource(R.drawable.ic_close), "Clear")
                            }
                        }
                    },
                    placeholder = {
                        Text("I really want to find...")
                    },
                    onSearch = {},
                    expanded = true,
                    onExpandedChange = {}
                )
            },
            onExpandedChange = {}
        ) {
            LazyColumn {
                if (screenModel.query.trim().isEmpty() && screenModel.results.isEmpty()) {
                    items(recentSearches.reversed()) { item ->
                        ListItem(
                            modifier = Modifier.clickable {
                                val placeDefinition = SimplePlaceDefinition(
                                    id = item.id,
                                    name = item.name,
                                    source = type
                                )
                                navigatorExtension.setResult("SearchScreen", placeDefinition)
                                coroutineScope.launch {
                                    screenModel.recentSearchesRepository.appendRecentSearch(placeDefinition)
                                }
                                navigator.pop()
                            },
                            headlineContent = {
                                Text(item.name)
                            },
                            leadingContent = {
                                Icon(painter = painterResource(R.drawable.ic_search_activity), contentDescription = "Recent Search")
                            },
                        )
                    }
                } else {
                    items(screenModel.results) { item ->
                        ListItem(
                            modifier = Modifier.clickable {
                                val placeDefinition = SimplePlaceDefinition(
                                    id = if (item.type == Type.STOP) item.id else "${item.lat.toDouble()},${item.lon.toDouble()}",
                                    name = item.name,
                                    source = type
                                )
                                navigatorExtension.setResult("SearchScreen", placeDefinition)
                                coroutineScope.launch {
                                    screenModel.recentSearchesRepository.appendRecentSearch(placeDefinition)
                                }
                                navigator.pop()
                            },
                            headlineContent = {
                                Text(item.name)
                            },
                            leadingContent = {
                                Icon(painter = painterResource(item.type.icon()), contentDescription = item.type.toString())
                            },
                            supportingContent = {
                                Text(
                                    text = when (item.type) {
                                        Type.ADDRESS -> buildString {
                                            if (item.street?.isNotEmpty() ?: false) append(item.street)
                                            if (item.houseNumber?.isNotEmpty() ?: false) append(" ${item.houseNumber}")
                                            if (item.zip?.isNotEmpty() ?: false) append(", ${item.zip}")
                                            item.areas.subList(0, 1).forEachIndexed { index, area ->
                                                append(area.name)
                                                if (index < item.areas.size - 1) append(", ")
                                            }
                                        }
                                        Type.PLACE,
                                        Type.STOP -> buildString {
                                            item.areas.forEachIndexed { index, area ->
                                                append(area.name)
                                                if (index < item.areas.size - 1) append(", ")
                                            }
                                        }
                                    }
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}
