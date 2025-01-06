package com.example.UF1_Proyecto.data.database.entities

import androidx.room.*
import java.sql.Date

// Entidad Ruta
@Entity(
    tableName = "rutas",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["idUsuario"],
        childColumns = ["idUsuario"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RouteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_ruta") val idRuta: Int = 0,
    @ColumnInfo(name = "nombre_ruta") val nombreRuta: String,
    @ColumnInfo(name = "distancia") val distancia: Double,
    @ColumnInfo(name = "fecha_realizacion") val fechaRealizacion: Date,
    @ColumnInfo(name = "ubicaciones") val ubicaciones: List<Location>,
    @ColumnInfo(name = "idUsuario") val idUsuario: Int
)