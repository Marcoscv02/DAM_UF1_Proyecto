package com.example.UF1_Proyecto.data.database.dao

import androidx.room.*
import com.example.UF1_Proyecto.data.database.entities.LocationEntity
import com.example.UF1_Proyecto.data.database.entities.RouteEntity

@SuppressWarnings("all")  // Esto suprime todas las advertencias en la interfaz
@Dao
interface RouteDao {

    //Create Route
    // Insert a single Route
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRoute(route: RouteEntity): Long

    // Insert a list of Routes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createListRoutes(routes: List<RouteEntity>): List<Long>

    //Delete Route
    @Delete
    suspend fun delete(route: RouteEntity)

    //Modify Route
    @Update
    suspend fun update(route: RouteEntity)

    //Select Route
    // Get all Routes
    @Query("SELECT * FROM route")
    suspend fun getAllRoutes(): List<RouteEntity>

    // Get a Route by ID
    @Query("SELECT * FROM route WHERE route_id = :routeId")
    suspend fun getRouteById(routeId: Int): RouteEntity?

    // Get Routes associated with a specific User (if any)
    @Query("SELECT * FROM route WHERE user_id = :userId")
    suspend fun getRoutesByUser(userId: Int): List<RouteEntity>

    //Get Locations associated with a specific route
    @Query("SELECT * FROM location WHERE route_id = :routeId")
    suspend fun getLocationsByRoute(routeId: Int): List<LocationEntity>


}