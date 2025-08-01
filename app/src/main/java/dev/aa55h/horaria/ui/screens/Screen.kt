package dev.aa55h.horaria.ui.screens

import androidx.annotation.DrawableRes
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SearchQuery
import dev.aa55h.horaria.ui.screens.search.place.PlaceSearchSource
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    val label: String,
    @param:DrawableRes val icon: Int? = null,
    val hideScaffoldBars: Boolean = false,
    val topBarBackArrow: Boolean = false
) {
    @Serializable data object Home : Screen("Home", R.drawable.ic_home)
    @Serializable data object Search : Screen("Search", R.drawable.ic_search)
    @Serializable data object Library : Screen("Library", R.drawable.ic_library_books)
    @Serializable data class PlaceSearch(val source: PlaceSearchSource = PlaceSearchSource.GENERIC) : Screen("Place Search", hideScaffoldBars = true)
    @Serializable data class SearchResults(val searchQuery: SearchQuery) : Screen("Search Results")

    companion object {
        val bottomNavItems = listOf(Home, Search, Library)
        val allScreens = listOf(
            Home.javaClass,
            Search.javaClass,
            Library.javaClass,
            PlaceSearch::class.java,
            SearchResults::class.java
        )

        fun getScreenByClassName(name: String?): Screen? {
            if (name == null) return null
            return allScreens.find { name.startsWith(it.name.replace('$', '.')) } as Screen?
        }
    }
}
