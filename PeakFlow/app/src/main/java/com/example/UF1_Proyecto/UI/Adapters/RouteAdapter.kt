package com.example.UF1_Proyecto.UI.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.PeakFlow.databinding.ItemRouteBinding
import com.example.UF1_Proyecto.data.database.entities.RouteEntity

/**
 * Adapter encargado de gestionar la visualización de las rutas en el RecyclerView.
 *
 * @property routes Lista de rutas que se mostrarán en el RecyclerView.
 */
class RouteAdapter(private var routes: List<RouteEntity>) : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    /**
     * ViewHolder para enlazar los elementos visuales de la ruta con la vista.
     *
     * @param binding Binding que contiene las vistas relacionadas con la ruta.
     */
    inner class RouteViewHolder(private val binding: ItemRouteBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Vincula los datos de una ruta al ViewHolder.
         *
         * @param route Objeto [RouteEntity] que contiene los detalles de la ruta.
         */
        fun bind(route: RouteEntity) {
            binding.tvRouteName.text = route.nombreRuta
            binding.tvDistance.text = "${route.distancia} km"
        }
    }

    /**
     * Crea un nuevo ViewHolder inflando el diseño del item_route.
     *
     * @param parent Vista principal del RecyclerView donde se colocarán los ítems.
     * @param viewType Tipo de vista.
     * @return Un nuevo ViewHolder asociado al diseño del item_route.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val binding = ItemRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RouteViewHolder(binding)
    }

    /**
     * Vincula los datos actuales de la lista al ViewHolder en la posición dada.
     *
     * @param holder El ViewHolder que debe ser actualizado.
     * @param position La posición en la lista de la que se deben extraer los datos.
     */
    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(routes[position])
    }

    /**
     * Devuelve el número total de elementos en la lista de rutas.
     *
     * @return El número total de elementos.
     */
    override fun getItemCount(): Int {
        return routes.size
    }

    /**
     * Actualiza la lista de rutas mostrada en el RecyclerView.
     *
     * @param newRoutes Nueva lista de rutas que reemplaza la anterior.
     */
    fun updateRoutes(newRoutes: List<RouteEntity>?) {
        if (newRoutes.isNullOrEmpty()) {
            routes = emptyList()
            notifyDataSetChanged()
            Log.d("DEBUG", "No se obtuvieron rutas válidas")
        } else {
            this.routes = newRoutes
            notifyDataSetChanged()
        }
    }

}
