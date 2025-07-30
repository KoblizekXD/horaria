package dev.aa55h.horaria.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.navigation.NavController
import dev.aa55h.horaria.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String, @param:DrawableRes val icon: Int, val label: String) {
    @Serializable data object Home : Screen("home", R.drawable.ic_home, "Home")
    @Serializable data object Search : Screen("search", R.drawable.ic_search, "Search")
    @Serializable data object Library : Screen("library", R.drawable.ic_library_books, "Library")

    companion object {
        val bottomNavItems = listOf(Home, Search, Library)

        fun getScreenByName(name: String?): Screen? {
            if (name == null) return null
            return bottomNavItems.find { it.javaClass.name.replace('$', '.') == name }
        }
    }
}

fun NavController.getCurrentScreen(): Screen? {
    val currentRoute = currentDestination?.route ?: return null
    Log.d("NavController", "Current route: $currentRoute")
    return Screen.getScreenByName(currentRoute)
}