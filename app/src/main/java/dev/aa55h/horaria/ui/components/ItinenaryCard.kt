package dev.aa55h.horaria.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.EncodedPolyline
import dev.aa55h.horaria.data.model.Itinerary
import dev.aa55h.horaria.data.model.Leg
import dev.aa55h.horaria.data.model.Mode
import dev.aa55h.horaria.data.model.Place
import dev.aa55h.horaria.ui.theme.AppTheme
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ofPattern

@Composable
fun ItineraryCard(
    itinerary: Itinerary,
    start: String,
    end: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card {
        Column(
            modifier = Modifier.clickable { expanded = !expanded }
                .padding(12.dp).then(modifier)
        ) {
            Text(
                text = "${itinerary.startTime.toLocalDateTime().formatted("dd.MM HH:mm")} " +
                        "― ${itinerary.endTime.toLocalDateTime().formatted("dd.MM HH:mm")} " +
                        if (expanded) "(${formatDuration(itinerary.duration)})" else "",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
            if (!expanded) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = start,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = end,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                Text(
                    text = "${itinerary.transfers} transfers, ${formatDuration(itinerary.duration)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text("Includes: ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                    itinerary.legs.map { it.mode }.distinct().forEach { mode ->
                        Icon(
                            painter = painterResource(mode.icon()),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(text = mode.pretty(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            } else {
                itinerary.legs.forEachIndexed { index, leg ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (index == 0) start else leg.from.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(PaddingValues(
                            horizontal = 12.dp
                        )),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VerticalStripedLine(
                            height = 48.dp
                        )
                        Icon(
                            painter = painterResource(leg.mode.icon()),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(text = leg.mode.pretty(), fontSize = 14.sp)
                        "${leg.distance?.toInt()}m (${formatDuration(leg.duration)})"
                        Text(
                            text = buildString {
                                if (leg.distance != null) append("${leg.distance.toInt()}m ")
                                append("(${formatDuration(leg.duration)})")
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (index == itinerary.legs.lastIndex) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = end,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
                TextButton(onClick = {}) {
                    Text(
                        text = "Show details",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ItineraryCardPreview() {
    val place = Place(
        name = "Praha, hl. n.",
        lat = 0.0,
        lon = 0.0,
        level = 0.0
    )
    
    AppTheme(darkTheme = true) {
        ItineraryCard(
            itinerary = Itinerary(
                duration = 534,
                startTime = "2023-10-01T12:00:00",
                endTime = "2023-10-01T12:09:54",
                transfers = 2,
                legs = listOf(
                    Leg(
                        mode = Mode.WALK,
                        startTime = "2023-10-01T12:00:00",
                        endTime = "2023-10-01T12:02:00",
                        from = place,
                        to = place,
                        distance = 200.0,
                        scheduledStartTime = "2023-10-01T12:00:00",
                        scheduledEndTime = "2023-10-01T12:02:00",
                        realTime = false,
                        scheduled = false,
                        duration = 111,
                        legGeometry = EncodedPolyline(""),
                    ),
                    Leg(
                        mode = Mode.WALK,
                        startTime = "2023-10-01T12:00:00",
                        endTime = "2023-10-01T12:02:00",
                        from = place,
                        to = place,
                        distance = 200.0,
                        scheduledStartTime = "2023-10-01T12:00:00",
                        scheduledEndTime = "2023-10-01T12:02:00",
                        realTime = false,
                        scheduled = false,
                        duration = 111,
                        legGeometry = EncodedPolyline(""),
                    ),
                ),
                fareTransfers = emptyList()
            ),
            start = "Praha",
            end = "Brno"
        )
    }
}

fun String.toLocalDateTime(): LocalDateTime {
    return ZonedDateTime.parse(this).toLocalDateTime()
}

fun LocalDateTime.formatted(format: String): String? {
    return this.format(ofPattern(format))
}

fun OffsetDateTime.formatted(format: String): String? {
    return this.format(ofPattern(format))
}

fun formatDuration(totalSeconds: Long): String {
    val days = totalSeconds / 86400
    val hours = (totalSeconds % 86400) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return buildString {
        if (days > 0) append("${days}d ")
        if (hours > 0 || days > 0) append("${hours}h ")
        if (minutes > 0 || hours > 0 || days > 0) append("${minutes}m ")
        if (seconds > 0 || minutes > 0 || hours > 0 || days > 0) append("${seconds}s")
    }.trim()
}

@Composable
fun VerticalStripedLine(
    stripeColor: Color = Color.Gray,
    height: Dp = 100.dp,
    backgroundColor: Color = Color.Transparent,
    stripeHeight: Dp = 4.dp,
    stripeSpacing: Dp = 4.dp,
    width: Dp = 2.dp,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val stripeHeightPx = with(density) { stripeHeight.toPx() }
    val stripeSpacingPx = with(density) { stripeSpacing.toPx() }

    Canvas(
        modifier
            .width(width)
            .height(height)
            .then(modifier)
    ) {
        // Fill background
        drawRect(backgroundColor)

        // Draw horizontal stripes stacked vertically
        var y = 0f
        while (y < size.height) {
            drawRect(
                color = stripeColor,
                topLeft = Offset(0f, y),
                size = Size(size.width, stripeHeightPx.coerceAtMost(size.height - y))
            )
            y += stripeHeightPx + stripeSpacingPx
        }
    }
}
