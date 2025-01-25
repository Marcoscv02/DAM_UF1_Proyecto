package com.example.UF1_Proyecto.data.database.entities

import androidx.room.*;


@Entity(
    tableName = "location",
    foreignKeys = [
        ForeignKey(
            entity = RouteEntity::class,
            parentColumns = ["route_id"],
            childColumns = ["route_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "location_id") val id: Int = 0,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "route_id") val routeId: Int // Clave foránea a RouteEntity
)
