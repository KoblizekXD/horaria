package dev.aa55h.horaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import dev.aa55h.horaria.ui.components.BottomBar
import dev.aa55h.horaria.ui.components.GenericTopBar
import dev.aa55h.horaria.ui.screens.ScreenDetails
import dev.aa55h.horaria.ui.screens.home.HomeScreen
import dev.aa55h.horaria.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalVoyagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Navigator(
                    screen = HomeScreen(),
                    disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false)
                ) {
                    Scaffold(
                        topBar = {
                            ScreenDetails.fromKClass(it.lastItem::class)?.let { details ->
                                if (details.showTopBar) {
                                    GenericTopBar(
                                        title = this.getString(details.title),
                                        navigator = it,
                                        displayBackArrow = details.showBackArrow
                                    )
                                }
                            }
                        },
                        bottomBar = {
                            ScreenDetails.fromKClass(it.lastItem::class)?.let { details ->
                                if (details.showBottomBar) {
                                    BottomBar(navigator = it)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            SlideTransition(navigator = it, disposeScreenAfterTransitionEnd = true)
                        }
                    }
                }
            }
        }
    }
}
