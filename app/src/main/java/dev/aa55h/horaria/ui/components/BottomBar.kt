package dev.aa55h.horaria.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.Navigator
import dev.aa55h.horaria.R
import dev.aa55h.horaria.ui.screens.ScreenDetails

@Composable
fun BottomBar(navigator: Navigator) {
    NavigationBar {
        ScreenDetails.onBottomBar.forEach { (screenDetails, screen) ->
            NavigationBarItem(
                selected = navigator.lastItem::class == screenDetails.kClass,
                onClick = {
                    navigator.replace(screen())
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screenDetails.icon ?: R.drawable.ic_home),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = screenDetails.title)
                }
            )
        }
    }
}
