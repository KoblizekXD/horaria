package dev.aa55h.horaria.ui.screens.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.aa55h.horaria.R
import dev.aa55h.horaria.ui.components.DateChip
import dev.aa55h.horaria.ui.components.TimeChip
import dev.aa55h.horaria.ui.screens.Screen
import dev.aa55h.horaria.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (viewModel.showPlaceSearch) {
            SearchBar(
                inputField = {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.from,
                        onValueChange = { viewModel.from = it },
                        placeholder = { Text(text = "From...") },
                        trailingIcon = {
                            Icon(painterResource(R.drawable.ic_search), contentDescription = "Search Icon")
                        }
                    )
                },
                expanded = true,
                onExpandedChange = {}
            ) {

            }
        }
        OutlinedTextField(
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Press) {
                                // navController.navigate(Screen.Finder(type = "from"))
                                viewModel.showPlaceSearch = true
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
                                navController.navigate(Screen.Finder(type = "to"))
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
        Button(onClick = { viewModel.onSearchClicked() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Search")
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SearchScreenPreview() {
    AppTheme(darkTheme = true) {
        SearchScreen(navController = rememberNavController())
    }
}