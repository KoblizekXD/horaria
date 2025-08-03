package dev.aa55h.horaria.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsTransit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import dev.aa55h.horaria.R

enum class Type {
    ADDRESS,
    PLACE,
    STOP;

    fun icon(): ImageVector = when (this) {
        ADDRESS -> Icons.Outlined.LocationOn
        PLACE -> Icons.Outlined.Home
        STOP -> Icons.Outlined.DirectionsTransit
    }

    @StringRes fun label(): Int = when (this) {
        ADDRESS -> R.string.type_address
        PLACE -> R.string.type_place
        STOP -> R.string.type_stop
    }
}