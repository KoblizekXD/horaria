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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import dev.aa55h.horaria.ui.components.DateChip
import dev.aa55h.horaria.ui.components.TimeChip

class SearchScreen: Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SearchScreenModel() }
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
            )

            PlaceSearchInput(
                screenModel = screenModel,
                placeholder = {
                    Text("From...")
                },
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
                // TODO
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
        icon: @Composable () -> Unit = {}
    ) {
        OutlinedTextField(
            readOnly = true,
            modifier = Modifier.fillMaxWidth().then(modifier),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Press) {
                                // TODO
                            }
                        }
                    }
                },
            placeholder = placeholder,
            value = screenModel.from?.name ?: "",
            trailingIcon = {
                icon
            },
            onValueChange = {},
        )
    }
}