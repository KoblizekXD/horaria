package dev.aa55h.horaria.ui.screens

import androidx.annotation.DrawableRes
import dev.aa55h.horaria.R

sealed class Screen(val route: String, @param:DrawableRes val icon: Int, val label: String) {
    data object Home : Screen("home", R.drawable.ic_home, "Home")
    data object Search : Screen("search", R.drawable.ic_search, "Search")
    data object Library : Screen("library", R.drawable.ic_library_books, "Library")
    data object Finder : Screen("finder", R.drawable.ic_search, "Finder")

    companion object {
        val bottomNavItems = listOf(Home, Search, Library)
        fun getScreenByRoute(route: String): Screen? {
            return bottomNavItems.find { it.route == route }
        }
    }
}
