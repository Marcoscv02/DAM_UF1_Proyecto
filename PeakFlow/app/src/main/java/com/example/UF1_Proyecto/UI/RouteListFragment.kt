package com.example.UF1_Proyecto.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PeakFlow.R
import com.example.PeakFlow.databinding.FragmentRouteListBinding
import com.example.UF1_Proyecto.UI.Adapters.RouteAdapter
import com.example.UF1_Proyecto.data.database.PeakFlowDataBase
import com.example.UF1_Proyecto.data.utils.SharedPreferencesLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragmento que muestra la lista de rutas disponibles para el usuario logueado.
 */
class RouteListFragment : Fragment() {

    // Variable para el binding generado por el View Binding
    private var _binding: FragmentRouteListBinding? = null
    private val binding get() = _binding!!

    // Variable para el adaptador del RecyclerView
    private lateinit var routeAdapter: RouteAdapter

    /**
     * Método encargado de inflar la vista del fragmento y configurar los elementos del UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista con el binding
        _binding = FragmentRouteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Método que se llama después de que la vista es creada.
     * Configura el RecyclerView y carga los datos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView con el adaptador
        routeAdapter = RouteAdapter(emptyList())
        binding.routeRecyclerView.apply {
            adapter = routeAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Configurar el botón para navegar a la creación de rutas
        binding.addRoute.setOnClickListener {
            view.findNavController().navigate(R.id.action_route_ListFragment_to_create_RouteFragment)
        }

        // Cargar y mostrar las rutas
        loadRoutes()
    }

    /**
     * Carga las rutas del usuario logueado desde la base de datos y actualiza el RecyclerView.
     */
    private fun loadRoutes() {
        val userId = SharedPreferencesLogin.getUserId(requireContext())
        println("USER ID: "+userId)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val db = PeakFlowDataBase.getDatabase(requireContext())
                val userRoutes = db.routeDao().getRoutesByUser(userId)

                withContext(Dispatchers.Main) {
                    Log.d("DEBUG", "Rutas obtenidas para el usuario $userId: $userRoutes")
                    routeAdapter.updateRoutes(userRoutes)
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Error al cargar las rutas: ${e.message}")
                withContext(Dispatchers.Main) {
                    // Manejar el caso de error si es necesario
                }
            }
        }
    }

    /**
     * Método que se llama cuando la vista se destruye para liberar recursos.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
