package dev.aa55h.horaria.ui.screens

import cafe.adriel.voyager.core.screen.Screen
import dev.aa55h.horaria.R
import dev.aa55h.horaria.ui.screens.home.HomeScreen
import dev.aa55h.horaria.ui.screens.search.SearchScreen
import dev.aa55h.horaria.ui.screens.search.place.PlaceSearchScreen
import dev.aa55h.horaria.ui.screens.search.results.SearchResultScreen
import kotlin.reflect.KClass

enum class ScreenDetails(
    val kClass: KClass<out Screen>,
    val title: Int,
    val icon: Int? = null,
    val showTopBar: Boolean = true,
    val showBottomBar: Boolean = true,
) {
    HOME(HomeScreen::class, R.string.title_home, icon = R.drawable.ic_home),
    SEARCH(SearchScreen::class, R.string.title_search, icon = R.drawable.ic_search),
    PLACE_SEARCH(PlaceSearchScreen::class, R.string.title_search, showTopBar = false, showBottomBar = false),
    SEARCH_RESULT(SearchResultScreen::class, R.string.title_search_results, showTopBar = false);

    companion object {
        fun fromKClass(kClass: KClass<out Screen>): ScreenDetails? {
            return entries.find { it.kClass == kClass }
        }

        val onBottomBar = mapOf<ScreenDetails, () -> Screen>(
            HOME to { HomeScreen() },
            SEARCH to { SearchScreen() },

        )
    }
}