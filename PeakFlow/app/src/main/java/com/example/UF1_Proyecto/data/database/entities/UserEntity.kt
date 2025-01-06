package com.example.UF1_Proyecto.data.database.entities

import androidx.room.*


// Entidad Usuario
@Entity(tableName = "usuarios")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_Usuario") val idUsuario: Int = 0,
    @ColumnInfo(name = "nombre_usuario") val nombreUsuario: String,
    @ColumnInfo(name = "contraseña") val contraseña: String,
    @ColumnInfo(name = "nombre_completo") val nombreCompleto: String,
    @ColumnInfo(name = "email") val email: String
)
