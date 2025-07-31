package dev.aa55h.horaria.ui.screens

import androidx.annotation.DrawableRes
import androidx.navigation.NavController
import dev.aa55h.horaria.R
import dev.aa55h.horaria.ui.screens.search.place.PlaceSearchSource
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(@param:DrawableRes val icon: Int, val label: String, val hideScaffoldBars: Boolean = false) {
    @Serializable data object Home : Screen(R.drawable.ic_home, "Home")
    @Serializable data object Search : Screen(R.drawable.ic_search, "Search")
    @Serializable data object Library : Screen(R.drawable.ic_library_books, "Library")
    @Serializable data class PlaceSearch(val source: PlaceSearchSource) : Screen(R.drawable.ic_search, "Place Search", hideScaffoldBars = true)

    companion object {
        val bottomNavItems = listOf(Home, Search, Library)
        val allScreens = bottomNavItems + PlaceSearch

        fun getScreenByClassName(name: String?): Screen? {
            if (name == null) return null
            return allScreens.find { it.javaClass.name.replace('$', '.') == name } as Screen?
        }
    }
}

fun NavController.getCurrentScreen(): Screen? {
    val currentRoute = currentDestination?.route ?: return null
    return Screen.getScreenByClassName(currentRoute)
}