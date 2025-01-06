package com.example.UF1_Proyecto.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.PeakFlow.R
import com.example.UF1_Proyecto.data.database.DatabaseProvider
import com.example.UF1_Proyecto.data.database.dao.RouteUserDao
import com.example.UF1_Proyecto.data.database.entities.Location
import com.example.UF1_Proyecto.data.database.entities.RouteEntity
import com.example.UF1_Proyecto.data.database.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa la base de datos
        val db = DatabaseProvider.getDatabase(this)
        val dao = db.rutaUsuarioDao()

        // Operaciones básicas
        CoroutineScope(Dispatchers.IO).launch {
            // 1. Insertar un usuario
            val usuarioId = insertarUsuario(dao)
            if (usuarioId > 0) {
                mostrarMensaje("Usuario insertado con ID: $usuarioId")
            }

            // 2. Insertar una ruta asociada al usuario
            val rutaId = insertarRuta(dao, usuarioId.toInt())
            if (rutaId > 0) {
                mostrarMensaje("Ruta insertada con ID: $rutaId")
            }

            // 3. Consultar rutas por usuario
            val rutas = consultarRutasPorUsuario(dao, usuarioId.toInt())
            rutas.forEach { RouteEntity ->
                mostrarMensaje("Ruta encontrada: ${RouteEntity.nombreRuta}, Distancia: ${RouteEntity.distancia} km")
            }
        }
    }

    /**
     * Inserta un usuario en la base de datos
     */
    private suspend fun insertarUsuario(dao: RouteUserDao): Long {
        val usuario = UserEntity(
            nombreUsuario = "jdoe",
            contraseña = "password123",
            nombreCompleto = "John Doe",
            email = "jdoe@example.com"
        )
        return dao.insertarUsuario(usuario)
    }

    /**
     * Inserta una ruta en la base de datos
     */
    private suspend fun insertarRuta(dao: RouteUserDao, usuarioId: Int): Long {
        val ruta = RouteEntity(
            nombreRuta = "Caminata en el parque",
            distancia = 3.5,
            fechaRealizacion = Date(),
            ubicaciones = listOf(
                Location(latitud = 40.7128, longitud = -74.0060),
                Location(latitud = 40.7138, longitud = -74.0050)
            ),
            idUsuario = usuarioId
        )
        return dao.insertarRuta(ruta)
    }

    /**
     * Consulta rutas por usuario
     */
    private suspend fun consultarRutasPorUsuario(dao: RouteUserDao, usuarioId: Int): List<RouteEntity> {
        return dao.obtenerRutasPorUsuario(usuarioId)
    }

    /**
     * Muestra un mensaje en el hilo principal
     */
    private fun mostrarMensaje(mensaje: String) {
        runOnUiThread {
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }
    }
}
