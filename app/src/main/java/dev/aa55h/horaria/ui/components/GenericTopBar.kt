package dev.aa55h.horaria.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import dev.aa55h.horaria.R
import dev.aa55h.horaria.ui.screens.home.HomeScreen
import dev.aa55h.horaria.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopBar(
    title: String,
    navigator: Navigator,
    displayBackArrow: Boolean = false
) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Default) },
        navigationIcon = {
            if (displayBackArrow) {
                IconButton(onClick = {
                    navigator.pop()
                }) {
                    Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = "Back")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    AppTheme(darkTheme = true) {
        Navigator(listOf(HomeScreen())) {
            GenericTopBar(title = "Home", navigator = it, displayBackArrow = true)
        }
    }
}