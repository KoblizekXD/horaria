package dev.aa55h.horaria.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.aa55h.horaria.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceSearchBar(
    modifier: Modifier = Modifier,
    open: MutableState<Boolean>
) {
    if (open.value) {
        SearchBar(
            windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            modifier = Modifier.fillMaxSize()
                .consumeWindowInsets(WindowInsets(0.dp, 0.dp, 0.dp, 0.dp))
                .wrapContentHeight(Alignment.Top)
                .then(modifier),
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            inputField = {
                SearchBarDefaults.InputField(
                    query = "",
                    onQueryChange = {  },
                    onSearch = {

                    },
                    leadingIcon = {
                        IconButton({
                            open.value = false
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

        }
    }
}