package com.example.UF1_Proyecto.data.database.entities

import androidx.room.*


// Entidad Usuario
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val userId: Int = 0,
    @ColumnInfo(name = "user_name") val userName : String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "real_name") val realName: String?,
    @ColumnInfo(name = "email") val email: String?
)
