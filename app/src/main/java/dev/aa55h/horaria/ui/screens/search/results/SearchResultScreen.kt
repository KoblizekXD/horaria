package dev.aa55h.horaria.ui.screens.search.results

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SimplePlaceDefinition
import dev.aa55h.horaria.ui.components.ItineraryCard
import dev.aa55h.horaria.ui.components.formatted
import dev.aa55h.horaria.utils.LocalDateTimeParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.time.LocalDateTime

@TypeParceler<LocalDateTime, LocalDateTimeParceler>()
@Parcelize
class SearchResultScreen(
    private val from: SimplePlaceDefinition,
    private val to: SimplePlaceDefinition,
    private val dateTime: LocalDateTime
): Screen, Parcelable {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<SearchResultScreenModel>()

        LaunchedEffect(Unit) {
            screenModel.findRoutes(from.id, to.id, dateTime)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(12.dp, 48.dp, 0.dp, 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(from.name, fontSize = 28.sp, overflow = TextOverflow.Ellipsis)
                    Icon(painter = painterResource(R.drawable.ic_arrow_right), contentDescription = null, modifier = Modifier.size(28.dp))
                    Text(to.name, fontSize = 28.sp, overflow = TextOverflow.Ellipsis)
                }
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp, 0.dp, 12.dp, 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        dateTime.formatted("dd MMMM yyyy, HH:mm")!!,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            if (screenModel.loading) {
                item {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 0.dp, 12.dp, 48.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            } else {
                items(screenModel.results!!.itineraries) {
                    ItineraryCard(
                        it, modifier = Modifier.fillMaxWidth(),
                        start = from.name,
                        end = to.name
                    )
                }
            }
        }
    }
}