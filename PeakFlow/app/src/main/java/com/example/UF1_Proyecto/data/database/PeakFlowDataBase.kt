package com.example.UF1_Proyecto.data.database

import androidx.room.*
import com.example.UF1_Proyecto.data.database.converters.DateConverter
import com.example.UF1_Proyecto.data.database.converters.LocaleConverter
import com.example.UF1_Proyecto.data.database.dao.LocationDao
import com.example.UF1_Proyecto.data.database.dao.RouteDao
import com.example.UF1_Proyecto.data.database.dao.UserDao
import com.example.UF1_Proyecto.data.database.entities.LocationEntity
import com.example.UF1_Proyecto.data.database.entities.RouteEntity
import com.example.UF1_Proyecto.data.database.entities.UserEntity

// Base de datos
@Database(
    entities = [
        UserEntity::class,
        RouteEntity::class,
        LocationEntity::class
               ],
    version = 2)
@TypeConverters(DateConverter::class, LocaleConverter::class)
abstract class PeakFlowDataBase : RoomDatabase() {
    abstract fun userDao ():UserDao
    abstract fun routeDao ():RouteDao
    abstract fun locationDao():LocationDao

    companion object {
        // Aquí está la función que crea o devuelve la instancia de la base de datos
        @Volatile
        private var INSTANCE: PeakFlowDataBase? = null

        fun getDatabase(context: android.content.Context): PeakFlowDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PeakFlowDataBase::class.java,
                    "peak_flow_database"
                )
                    .createFromAsset("peak_flow_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}