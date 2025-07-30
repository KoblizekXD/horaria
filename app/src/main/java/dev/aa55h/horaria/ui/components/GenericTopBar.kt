package dev.aa55h.horaria.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.aa55h.horaria.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopBar(
    title: String,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NavigationBarDefaults.containerColor
        ),
        title = { Text(text = title, fontWeight = FontWeight.SemiBold) }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    AppTheme(darkTheme = true) {
        GenericTopBar(title = "Home")
    }
}