package dev.aa55h.horaria.ui.screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import dev.aa55h.horaria.ui.screens.search.place.PlaceSearchScreenModel
import dev.aa55h.horaria.ui.screens.search.results.SearchResultScreenModel

@Module
@InstallIn(ActivityComponent::class)
abstract class ScreenModelModule {
    @Binds
    @IntoMap
    @ScreenModelKey(PlaceSearchScreenModel::class)
    abstract fun bindPlaceSearchScreenModel(placeSearchScreenModel: PlaceSearchScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(SearchResultScreenModel::class)
    abstract fun bindSearchResultScreenModel(searchResultScreenModel: SearchResultScreenModel): ScreenModel
}