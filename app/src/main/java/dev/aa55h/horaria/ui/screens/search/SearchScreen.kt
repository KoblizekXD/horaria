package dev.aa55h.horaria.ui.screens.search

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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.aa55h.horaria.R
import dev.aa55h.horaria.ui.components.DateChip
import dev.aa55h.horaria.ui.components.PlaceSearchBar
import dev.aa55h.horaria.ui.components.TimeChip
import dev.aa55h.horaria.ui.screens.Screen
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
                                    // navController.navigate(Screen.Finder(type = "from"))
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
            Button(onClick = { viewModel.onSearchClicked() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Search")
            }
        }
        PlaceSearchBar(
            open = viewModel.showPlaceSearch,
            modifier = Modifier.align(Alignment.TopStart)
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