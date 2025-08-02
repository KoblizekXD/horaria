package dev.aa55h.horaria.data.model

import dev.aa55h.horaria.R

enum class Type {
    ADDRESS,
    PLACE,
    STOP;

    fun icon() = when (this) {
        ADDRESS -> R.drawable.ic_pin_drop
        PLACE -> R.drawable.ic_location_city
        STOP -> R.drawable.ic_direction
    }
}