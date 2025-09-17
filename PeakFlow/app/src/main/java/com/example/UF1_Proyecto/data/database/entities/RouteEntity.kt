package com.example.UF1_Proyecto.data.database.entities

import androidx.annotation.NonNull
import androidx.room.*

// Entidad Ruta
@Entity(
    tableName = "route",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RouteEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "route_id")
    val idRuta: Int = 0,

    @ColumnInfo(name = "route_name")
    val nombreRuta: String,

    @ColumnInfo(name = "route_time")
    val tiempoRuta: Long? = 0,

    @ColumnInfo(name = "average_speed")
    val velMedia: Double? = 0.0,

    @ColumnInfo(name = "distance")
    val distancia: Double? = 0.0,

    @ColumnInfo(name = "track_date")
    val fechaRealizacion: Long? = null,

    @ColumnInfo(name = "user_id")
    val idUsuario: Int
)