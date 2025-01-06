package com.example.UF1_Proyecto.data.database

import androidx.room.*
import com.example.UF1_Proyecto.data.database.dao.RouteUserDao
import com.example.UF1_Proyecto.data.database.entities.DateConverter
import com.example.UF1_Proyecto.data.database.entities.LocaleConverter
import com.example.UF1_Proyecto.data.database.entities.RouteEntity
import com.example.UF1_Proyecto.data.database.entities.UserEntity

// Base de datos
@Database(entities = [UserEntity::class, RouteEntity::class], version = 1)
@TypeConverters(DateConverter::class, LocaleConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rutaUsuarioDao(): RouteUserDao
}