package dev.aa55h.horaria.ui.screens

import androidx.annotation.DrawableRes
import dev.aa55h.horaria.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String, @param:DrawableRes val icon: Int, val label: String) {
    @Serializable data object Home : Screen("home", R.drawable.ic_home, "Home")
    @Serializable data object Search : Screen("search", R.drawable.ic_search, "Search")
    @Serializable data object Library : Screen("library", R.drawable.ic_library_books, "Library")

    companion object {
        val bottomNavItems = listOf(Home, Search, Library)
        fun getScreenByLabel(label: String): Screen? {
            return bottomNavItems.find { it.label == label }
        }
    }
}
