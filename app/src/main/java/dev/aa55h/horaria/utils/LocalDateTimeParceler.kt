package dev.aa55h.horaria.utils

import android.os.Parcel
import kotlinx.parcelize.Parceler
import java.time.LocalDateTime

object LocalDateTimeParceler : Parceler<LocalDateTime> {
    override fun create(parcel: Parcel): LocalDateTime {
        val year = parcel.readInt()
        val month = parcel.readInt()
        val day = parcel.readInt()
        val hour = parcel.readInt()
        val minute = parcel.readInt()
        val second = parcel.readInt()
        val nano = parcel.readInt()
        return LocalDateTime.of(year, month, day, hour, minute, second, nano)
    }

    override fun LocalDateTime.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(year)
        parcel.writeInt(monthValue)
        parcel.writeInt(dayOfMonth)
        parcel.writeInt(hour)
        parcel.writeInt(minute)
        parcel.writeInt(second)
        parcel.writeInt(nano)
    }
}