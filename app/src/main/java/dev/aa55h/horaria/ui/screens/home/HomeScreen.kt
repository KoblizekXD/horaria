package dev.aa55h.horaria.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

class HomeScreen: Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        Text("Good morning")
    }
}