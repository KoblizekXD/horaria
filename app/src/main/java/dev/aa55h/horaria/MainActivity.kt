package dev.aa55h.horaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dev.aa55h.horaria.ui.components.BottomBar
import dev.aa55h.horaria.ui.components.GenericTopBar
import dev.aa55h.horaria.ui.screens.Screen
import dev.aa55h.horaria.ui.screens.finder.FinderScreen
import dev.aa55h.horaria.ui.screens.home.HomeScreen
import dev.aa55h.horaria.ui.screens.search.SearchScreen
import dev.aa55h.horaria.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                AppNavHost(rememberNavController())
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination?.route?.let { Screen.getScreenByRoute(it) }

    Scaffold(
        topBar = {
            currentScreen?.let {
                GenericTopBar(it.label)
            }
        },
        bottomBar = { BottomBar(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Home> { HomeScreen() }
            composable<Screen.Search> { SearchScreen(navController = navController) }
            composable<Screen.Finder> {
                val finder = it.toRoute<Screen.Finder>()
                FinderScreen(navController, finder.type)
            }
            // composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AppNavHostPreview() {
    AppTheme(darkTheme = true) {
        AppNavHost(rememberNavController())
    }
}