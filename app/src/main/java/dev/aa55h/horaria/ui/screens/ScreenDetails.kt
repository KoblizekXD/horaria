package dev.aa55h.horaria.ui.screens

import cafe.adriel.voyager.core.screen.Screen
import dev.aa55h.horaria.ui.screens.home.HomeScreen
import dev.aa55h.horaria.ui.screens.search.SearchScreen
import kotlin.reflect.KClass

enum class ScreenDetails(
    val kClass: KClass<out Screen>,
    val title: String,
    val icon: Int? = null,
    val showTopBar: Boolean = true,
    val showBottomBar: Boolean = true,
) {
    HOME(HomeScreen::class, "Home"),
    SEARCH(SearchScreen::class, "Search");

    companion object {
        fun fromKClass(kClass: KClass<out Screen>): ScreenDetails? {
            return entries.find { it.kClass == kClass }
        }

        fun fromTitle(title: String): ScreenDetails? {
            return entries.find { it.title == title }
        }

        val onBottomBar = mapOf<ScreenDetails, () -> Screen>(
            HOME to { HomeScreen() },
            SEARCH to { SearchScreen() },

        )
    }
}