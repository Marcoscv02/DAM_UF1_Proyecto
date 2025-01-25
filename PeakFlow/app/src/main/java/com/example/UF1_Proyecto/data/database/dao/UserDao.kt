package com.example.UF1_Proyecto.data.database.dao

import androidx.room.*

import com.example.UF1_Proyecto.data.database.entities.UserEntity

@SuppressWarnings("all")  // Esto suprime todas las advertencias en la interfaz
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createListUsers(users: List<UserEntity>): List<Long>

    @Delete
    suspend fun delete(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user WHERE user_id = :UserId")
    suspend fun getUserById(UserId: Int): UserEntity?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getClientsByEmail(email: String): UserEntity?
}
