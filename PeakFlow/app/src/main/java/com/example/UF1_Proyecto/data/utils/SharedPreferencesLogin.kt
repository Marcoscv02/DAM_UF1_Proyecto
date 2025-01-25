package com.example.UF1_Proyecto.data.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Singleton que proporciona funciones para manejar las preferencias compartidas relacionadas con el inicio de sesión del usuario.
 *
 * Utiliza SharedPreferences para almacenar y recuperar el ID del usuario logueado.
 */
object SharedPreferencesLogin {
    // Nombre del archivo de preferencias compartidas para el login
    private const val PREFS_NAME = "login_prefs"

    // Llave para almacenar el ID del usuario en SharedPreferences
    private const val KEY_USER_ID = "user_id"

    /**
     * Obtiene una instancia de SharedPreferences para almacenar datos persistentes.
     *
     * @param context Contexto de la aplicación
     * @return Una instancia de SharedPreferences configurada para el almacenamiento persistente
     */
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Guarda el ID del usuario en SharedPreferences.
     *
     * @param context Contexto de la aplicación
     * @param userId El ID del usuario que se quiere guardar
     */
    fun saveUser(context: Context, userId: Int) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()
        editor.putInt(KEY_USER_ID, userId)  // Almacena el ID del usuario
        editor.apply()  // Aplicar cambios de inmediato
    }

    /**
     * Recupera el ID del usuario desde SharedPreferences.
     *
     * @param context Contexto de la aplicación
     * @return El ID del usuario almacenado o -1 si no hay usuario logueado
     */
    fun getUserId(context: Context): Int {
        val prefs = getSharedPreferences(context)
        return prefs.getInt(KEY_USER_ID, -1)  // Devuelve -1 por defecto si no se encuentra el ID
    }

    /**
     * Limpia el ID del usuario de las preferencias compartidas.
     *
     * @param context Contexto de la aplicación
     */
    fun clearUser(context: Context) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()
        editor.clear()  // Limpia todas las preferencias
        editor.apply()  // Aplicar cambios de inmediato
    }

    /**
     * Verifica si hay un usuario logueado actualmente.
     *
     * @param context Contexto de la aplicación
     * @return Verdadero si hay un usuario logueado, falso en caso contrario
     */
    fun isUserLoggedIn(context: Context): Boolean {
        val userId = getUserId(context)
        return userId != -1  // Si el ID es distinto de -1, significa que hay un usuario logueado
    }
}
