package com.example.UF1_Proyecto.data.database.dao

import androidx.room.*
import com.example.UF1_Proyecto.data.database.entities.RouteEntity
import com.example.UF1_Proyecto.data.database.entities.UserEntity

// DAO para operaciones con las tablas
@Dao
interface RouteUserDao {
    @Insert
    suspend fun insertarUsuario(usuario: UserEntity): Long

    @Insert
    suspend fun insertarRuta(ruta: RouteEntity): Long

    @Query("SELECT * FROM rutas WHERE idUsuario = :usuarioId")
    suspend fun obtenerRutasPorUsuario(usuarioId: Int): List<RouteEntity>

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerUsuarios(): List<UserEntity>
}
