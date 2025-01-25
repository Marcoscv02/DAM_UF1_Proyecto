package com.example.UF1_Proyecto.data.database.dao

import androidx.room.*
import com.example.UF1_Proyecto.data.database.Relations.LocationWithRoute
import com.example.UF1_Proyecto.data.database.entities.LocationEntity

@SuppressWarnings("all")  // Esto suprime todas las advertencias en la interfaz
@Dao
interface LocationDao {

    //Insert Location:
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createLocation(location: LocationEntity):Long

    // Insert a list of Locations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createListLocations(locations: List<LocationEntity>):List<Long>

    @Transaction
    //Get q a specific location in a route
    @Query("SELECT * FROM location WHERE location_id = :locationId")
    suspend fun getLocationByRoute(locationId: Int):  List<LocationEntity>
}
