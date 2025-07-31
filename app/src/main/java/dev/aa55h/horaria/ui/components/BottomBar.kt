package dev.aa55h.horaria.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.aa55h.horaria.ui.screens.Screen
import dev.aa55h.horaria.ui.screens.getCurrentScreen
import dev.aa55h.horaria.ui.theme.AppTheme

@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar {
        Screen.bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(screen.icon), contentDescription = screen.label) },
                label = { Text(screen.label, fontFamily = FontFamily.Default) },
                selected = navController.getCurrentScreen() == screen,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BarPreview() {
    val navController = rememberNavController()
    AppTheme(darkTheme = true) {
        BottomBar(navController)
    }
}