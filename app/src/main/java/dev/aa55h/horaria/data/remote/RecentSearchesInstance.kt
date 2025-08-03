package dev.aa55h.horaria.data.remote

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aa55h.horaria.data.repository.RecentSearchesRepository

val Context.dataStore by preferencesDataStore(name = "options")

@Module
@InstallIn(SingletonComponent::class)
object RecentSearchesInstance {
    @Provides
    fun provideRecentSearchesRepository(@ApplicationContext context: Context): RecentSearchesRepository {
        val dataStore: DataStore<Preferences> = context.dataStore
        return RecentSearchesRepository(dataStore)
    }
}