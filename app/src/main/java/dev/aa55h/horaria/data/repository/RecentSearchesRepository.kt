package dev.aa55h.horaria.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.aa55h.horaria.data.model.SimplePlaceDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class RecentSearchesRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val RECENT_SEARCHES = stringPreferencesKey("recent_searches")
        const val MAX_RECENT_SEARCHES = 5
    }

    val recentSearches: Flow<Set<SimplePlaceDefinition>> = dataStore.data
        .map { preferences ->
            val string = preferences[RECENT_SEARCHES]
            if (string == null || string.isEmpty()) return@map emptySet()
            else return@map Json.decodeFromString(string)
        }

    suspend fun appendRecentSearch(placeDefinition: SimplePlaceDefinition) {
        dataStore.edit { preferences ->
            val currentSearches = preferences[RECENT_SEARCHES]?.let {
                Json.decodeFromString<Set<SimplePlaceDefinition>>(it)
            } ?: emptySet()

            if (currentSearches.any { it.id == placeDefinition.id }) {
                val updatedSearches = currentSearches - placeDefinition
                preferences[RECENT_SEARCHES] = Json.encodeToString(updatedSearches)
            }
            val updatedSearches = (currentSearches + placeDefinition)
                .toMutableList()
                .takeLast(MAX_RECENT_SEARCHES)
            preferences[RECENT_SEARCHES] = Json.encodeToString(updatedSearches.toSet())
        }
    }

    suspend fun removeRecentSearch(placeDefinition: SimplePlaceDefinition) {
        dataStore.edit { preferences ->
            val currentSearches = preferences[RECENT_SEARCHES]?.let {
                Json.decodeFromString<Set<SimplePlaceDefinition>>(it)
            } ?: emptySet()

            val updatedSearches = currentSearches - placeDefinition
            preferences[RECENT_SEARCHES] = Json.encodeToString(updatedSearches)
        }
    }
}