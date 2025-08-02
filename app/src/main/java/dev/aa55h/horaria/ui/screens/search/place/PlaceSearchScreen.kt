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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SearchAutocompleteResult
import dev.aa55h.horaria.data.model.SearchScreenSource
import dev.aa55h.horaria.data.model.SimplePlaceDefinition
import dev.aa55h.horaria.utils.rememberNavigationResultExtension

@OptIn(ExperimentalMaterial3Api::class)
class PlaceSearchScreen(private val type: SearchScreenSource): Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<PlaceSearchScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigatorExtension = rememberNavigationResultExtension()

        SearchBar(
            windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            modifier = Modifier.consumeWindowInsets(WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)),
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            expanded = true,
            inputField = {
                SearchBarDefaults.InputField(
                    query = screenModel.query,
                    onQueryChange = { screenModel.onQueryChange(it) },
                    leadingIcon = {
                        IconButton({
                            navigator.pop()
                        }) {
                            Icon(painterResource(R.drawable.ic_arrow_back), "Back")
                        }
                    },
                    onSearch = {},
                    expanded = true,
                    onExpandedChange = {}
                )
            },
            onExpandedChange = {}
        ) {
            LazyColumn {
                items(screenModel.results) { item ->
                    ListItem(
                        modifier = Modifier.clickable {
                            navigatorExtension.popWithResult(SimplePlaceDefinition(
                                id = item.id,
                                name = item.name,
                                source = type
                            ))
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
                                    SearchAutocompleteResult.Type.ADDRESS -> buildString {
                                        append(item.street)
                                        if (item.houseNumber.isNotEmpty()) append(" ${item.houseNumber}")
                                        if (item.zip.isNotEmpty()) append(", ${item.zip}")
                                        item.areas.subList(0, 1).forEachIndexed { index, area ->
                                            append(area.name)
                                            if (index < item.areas.size - 1) append(", ")
                                        }
                                    }
                                    SearchAutocompleteResult.Type.PLACE,
                                    SearchAutocompleteResult.Type.STOP -> buildString {
                                        item.areas.subList(0, 3).forEachIndexed { index, area ->
                                            append(area.name)
                                            if (index < 2) append(", ")
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
