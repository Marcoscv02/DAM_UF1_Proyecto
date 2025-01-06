package com.example.UF1_Proyecto.data.database.entities

import androidx.room.TypeConverter


class LocaleConverter {
    @TypeConverter
    fun fromLocationList(locations: List<Location>?): String {
        return locations?.joinToString(";") { "${it.latitud},${it.longitud}" } ?: ""
    }

    @TypeConverter
    fun toLocationList(data: String): List<Location> {
        if (data.isEmpty()) return emptyList()
        return data.split(";").map { pair ->
            val (latitude, longitude) = pair.split(",").map { it.toDouble() }
            Location(latitude, longitude)
        }
    }
}