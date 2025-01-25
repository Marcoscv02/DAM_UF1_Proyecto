package com.example.UF1_Proyecto.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.PeakFlow.R
import com.example.UF1_Proyecto.data.database.PeakFlowDataBase
import com.example.UF1_Proyecto.data.database.entities.LocationEntity
import com.example.UF1_Proyecto.data.database.entities.RouteEntity
import com.example.UF1_Proyecto.data.database.entities.UserEntity
import com.example.UF1_Proyecto.data.utils.SharedPreferencesLogin
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    /**
     * Instancia de la base de datos de Room.
     *
     * Utiliza `Room.databaseBuilder` para crear o acceder a la base de datos "PeakFlow" de la aplicación.
     * Esta base de datos se utilizará para almacenar información de  rutas y usuarios, etc.
     */
    private val peakFlowdb by lazy {
        PeakFlowDataBase.getDatabase(applicationContext)
    }



    /**
     * Metodo de inicialización de la vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //simula el logeo de un usuario
        simulateLogIn()

        //populateDatabase() //genera la base de datos con datos ficticios

    }


    /**
     * Simula el inicio de sesión de un usuario.
     *
     * Verifica si ya existe un usuario guardado en las preferencias compartidas. Si no es así, simula el inicio de sesión
     * y guarda el ID del usuario en las preferencias. Luego muestra un mensaje en pantalla.
     */
    private fun simulateLogIn() {
        SharedPreferencesLogin.clearUser(this)

        val userId = SharedPreferencesLogin.getUserId(this)

        if (userId != -1) {
            lifecycleScope.launch(Dispatchers.IO) {
                val db = PeakFlowDataBase.getDatabase(applicationContext)
                val user = db.userDao().getUserById(userId)

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        Toast.makeText(this@MainActivity, "Logueado como ${user.userName}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Error al recuperar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            // Mostrar una opción para seleccionar un usuario de prueba
            lifecycleScope.launch(Dispatchers.IO) {
                val db = PeakFlowDataBase.getDatabase(applicationContext)
                val users = db.userDao().getAllUsers()  // Obtener todos los usuarios disponibles

                withContext(Dispatchers.Main) {
                    if (users.isNotEmpty()) {
                        val userOptions = users.map { "${it.userName} (${it.realName})" }
                        val builder = android.app.AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("Selecciona un usuario de prueba")
                        builder.setItems(userOptions.toTypedArray()) { _, which ->
                            val selectedUserId = users[which].userId.toInt()  // Asegurarse de convertirlo al tipo entero
                            SharedPreferencesLogin.saveUser(this@MainActivity, selectedUserId.toInt())
                            Toast.makeText(this@MainActivity, "Logueado como ${users[which].userName}", Toast.LENGTH_LONG).show()
                        }
                        builder.show()
                    } else {
                        Toast.makeText(this@MainActivity, "No hay usuarios de prueba disponibles", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    /**
     * Población de la base de datos con datos de prueba.
     *
     * Este método llena la base de datos de Room con datos ficticios sobre usuarios, rutas y ubicaciones.
     * Los datos son insertados en las tablas correspondientes utilizando las entidades de la base de datos.
     */
    private fun populateDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            
            // Inserta datos de usuarios
            val users = listOf(
                UserEntity(
                    userName = "jdoe",
                    password = "password123",
                    realName = "John Doe",
                    email = "jdoe@example.com"
                ),
                UserEntity(
                    userName = "asmith",
                    password = "securePass",
                    realName = "Alice Smith",
                    email = "asmith@example.com"
                )
            )
            val userIds = peakFlowdb.userDao().createListUsers(users)

            // Inserta datos de rutas asociadas al usuario logueado
            val currentUserId = SharedPreferencesLogin.getUserId(this@MainActivity)  // Obtiene el userId guardado

            val routes = listOf(
                RouteEntity(
                    nombreRuta = "Mountain Trail",
                    distancia = 5.0,
                    fechaRealizacion = System.currentTimeMillis(),
                    idUsuario = userIds[0].toInt(),  // Asocia esta ruta al primer usuario
                ),
                RouteEntity(
                    nombreRuta = "City Walk",
                    distancia = 3.2,
                    fechaRealizacion = System.currentTimeMillis(),
                    idUsuario = userIds[1].toInt(),  // Asocia esta ruta al segundo usuario
                ),
                RouteEntity(
                    nombreRuta = "bike track",
                    distancia = 54.2,
                    fechaRealizacion = System.currentTimeMillis(),
                    idUsuario = userIds[1].toInt(),  // Asocia esta ruta al segundo usuario
                )
            )
            // Inserta rutas y captura sus IDs generados
            val routeIds = peakFlowdb.routeDao().createListRoutes(routes)

// Usa estos IDs generados para asociar ubicaciones
            val locations = listOf(
                LocationEntity(
                    latitude = 40.7128,
                    longitude = -74.0060,
                    routeId = routeIds[0].toInt()  // Asociar a la primera ruta
                ),
                LocationEntity(
                    latitude = 40.7138,
                    longitude = -74.0050,
                    routeId = routeIds[0].toInt()
                ),
                LocationEntity(
                    latitude = 37.7749,
                    longitude = -122.4194,
                    routeId = routeIds[1].toInt()  // Asociar a la segunda ruta
                )
            )

            locations.forEach { location -> peakFlowdb.locationDao().createLocation(location) }
        }
    }

}
