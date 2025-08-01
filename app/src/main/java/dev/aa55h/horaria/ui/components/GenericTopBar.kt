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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.aa55h.horaria.R
import dev.aa55h.horaria.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopBar(
    title: String,
    navController: NavHostController,
    displayBackArrow: Boolean = false
) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Default) },
        navigationIcon = {
            if (displayBackArrow) {
                IconButton(onClick = {
                    navController.popBackStack()
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
        GenericTopBar(title = "Home", navController = rememberNavController())
    }
}