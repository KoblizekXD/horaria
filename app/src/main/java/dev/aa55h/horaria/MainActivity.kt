package dev.aa55h.horaria

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import dev.aa55h.horaria.ui.components.BottomBar
import dev.aa55h.horaria.ui.components.GenericTopBar
import dev.aa55h.horaria.ui.screens.Screen
import dev.aa55h.horaria.ui.screens.home.HomeScreen
import dev.aa55h.horaria.ui.screens.search.SearchScreen
import dev.aa55h.horaria.ui.screens.search.SearchViewModel
import dev.aa55h.horaria.ui.screens.search.SearchedAndFoundPlace
import dev.aa55h.horaria.ui.screens.search.place.PlaceSearchScreen
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
    val currentScreen = Screen.getScreenByClassName(navBackStackEntry?.destination?.route)
    Log.d("AppNavHost", "Current screen: ${navBackStackEntry?.destination?.route}")

    Scaffold(
        topBar = {
            currentScreen?.let {
                if (!it.hideScaffoldBars) GenericTopBar(it.label)
            }
        },
        bottomBar = {
            if (currentScreen == null || (!currentScreen.hideScaffoldBars)) {
                BottomBar(navController, currentScreen = currentScreen ?: Screen.Home)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally { it } + fadeIn() },
            exitTransition = { slideOutHorizontally { -it } + fadeOut() },
            popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
            popExitTransition = { slideOutHorizontally { it } + fadeOut() },
        ) {
            composable<Screen.Home> { HomeScreen() }
            composable<Screen.Search> {
                SearchScreen(
                    navController = navController,
                    viewModel = hiltViewModel<SearchViewModel>().apply {
                        from = it.savedStateHandle.getLiveData<SearchedAndFoundPlace?>("from", null).value
                        to = it.savedStateHandle.getLiveData<SearchedAndFoundPlace?>("to", null).value
                    }
                )
            }
            composable<Screen.PlaceSearch>(
                enterTransition = { slideInVertically { it } + fadeIn() },
                exitTransition = { slideOutVertically { -it } + fadeOut() },
                popEnterTransition = { slideInVertically { -it } + fadeIn() },
                popExitTransition = { slideOutVertically { it } + fadeOut() }
            ) {
                PlaceSearchScreen(navController = navController, source = it.toRoute<Screen.PlaceSearch>().source)
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