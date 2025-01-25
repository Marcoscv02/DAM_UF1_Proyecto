package com.example.UF1_Proyecto.data.database.converters

import androidx.room.TypeConverter
import com.example.UF1_Proyecto.data.database.entities.LocationEntity


class LocaleConverter {
    @TypeConverter
    fun fromLocationList(locations: List<LocationEntity>?): String {
        return locations?.joinToString(";") { "${it.latitude},${it.longitude}" } ?: ""
    }

    @TypeConverter
    fun toLocationList(data: String): List<LocationEntity> {
        if (data.isEmpty()) return emptyList()

        return data.split(";").map { pair ->
            val (latitude, longitude) = pair.split(",").map { it.toDouble() }
            // Asignamos un valor predeterminado para routeId
            LocationEntity(latitude = latitude, longitude = longitude, routeId = 0)  // O puedes usar otro valor adecuado
        }
    }
}