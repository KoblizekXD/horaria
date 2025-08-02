package dev.aa55h.horaria.ui.screens.search

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.aa55h.horaria.data.model.SearchScreenSource
import dev.aa55h.horaria.data.model.SimplePlaceDefinition
import dev.aa55h.horaria.ui.components.DateChip
import dev.aa55h.horaria.ui.components.TimeChip
import dev.aa55h.horaria.ui.screens.search.place.PlaceSearchScreen
import dev.aa55h.horaria.ui.screens.search.results.SearchResultScreen
import dev.aa55h.horaria.utils.rememberNavigationResultExtension
import java.time.LocalDateTime

class SearchScreen: Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SearchScreenModel() }
        val navigator = LocalNavigator.currentOrThrow
        val navigatorExtension = rememberNavigationResultExtension()
        val context = LocalContext.current

        val value = navigatorExtension.getResult<SimplePlaceDefinition>("SearchScreen").value
        Log.d("SearchScreen", "Received result: $value")
        if (value != null) {
            if (value.source == SearchScreenSource.FROM) {
                screenModel.from = value
            } else if (value.source == SearchScreenSource.TO) {
                screenModel.to = value
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PlaceSearchInput(
                screenModel = screenModel,
                placeholder = {
                    Text("From...")
                },
                onClick = {
                    navigator.push(PlaceSearchScreen(SearchScreenSource.FROM, navigatorExtension))
                },
                value = screenModel.from?.name ?: ""
            )

            PlaceSearchInput(
                screenModel = screenModel,
                placeholder = {
                    Text("To...")
                },
                onClick = {
                    navigator.push(PlaceSearchScreen(SearchScreenSource.TO, navigatorExtension))
                },
                value = screenModel.to?.name ?: ""
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                DateChip(
                    onConfirm = { date ->
                        screenModel.date = date
                    },
                    date = screenModel.date
                )
                TimeChip(
                    onConfirm = { time ->
                        screenModel.time = time
                    },
                    time = screenModel.time
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                if (screenModel.from == null || screenModel.to == null) {
                    Log.w("SearchScreen", "From or To place is not set")
                    Toast.makeText(context, "Please select from and to places", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }
                navigator.push(SearchResultScreen(
                    from = screenModel.from!!,
                    to = screenModel.to!!,
                    dateTime = LocalDateTime.of(
                        screenModel.date ?: LocalDateTime.now().toLocalDate(),
                        screenModel.time ?: LocalDateTime.now().toLocalTime()
                    )
                ))
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Search")
            }
        }
    }

    @Composable
    private fun PlaceSearchInput(
        screenModel: SearchScreenModel,
        modifier: Modifier = Modifier,
        placeholder: @Composable () -> Unit = {},
        icon: @Composable () -> Unit = {},
        onClick: () -> Unit = {},
        value: String = "",
    ) {
        OutlinedTextField(
            readOnly = true,
            modifier = Modifier.fillMaxWidth().then(modifier),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Press) {
                                onClick()
                            }
                        }
                    }
                },
            placeholder = placeholder,
            value = value,
            trailingIcon = {
                icon
            },
            onValueChange = {},
        )
    }
}