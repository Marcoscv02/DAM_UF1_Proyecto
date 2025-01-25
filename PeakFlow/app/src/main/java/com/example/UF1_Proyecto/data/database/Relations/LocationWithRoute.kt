package com.example.UF1_Proyecto.data.database.Relations

import androidx.room.*
import com.example.UF1_Proyecto.data.database.entities.LocationEntity
import com.example.UF1_Proyecto.data.database.entities.RouteEntity

data class LocationWithRoute(
    @Embedded val location: LocationEntity,
    @Relation(
        parentColumn = "route_id",
        entityColumn = "route_id"
    )
    val route: RouteEntity,
)
