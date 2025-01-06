package com.example.UF1_Proyecto.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.UF1_Proyecto.R
import com.example.UF1_Proyecto.databinding.FragmentRouteListBinding

class RouteListFragment : Fragment() {

    private var _binding: FragmentRouteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentRouteListBinding.inflate(inflater, container, false)

            // Obtiene la raíz de la vista del binding
            val view = binding.root

        // Asigna un evento al botón para navegar a la siguiente pantalla
        binding.addRoute.setOnClickListener {
            // Navega al fragmento de la lista de rutas cuando se hace clic en el botón
            view.findNavController().navigate(R.id.action_route_ListFragment_to_create_RouteFragment)
        }

            return view
    }

    // Método que se llama cuando la vista se destruye para evitar fugas de memoria
    override fun onDestroyView() {
        super.onDestroyView()
        // Libera el binding al destruir la vista
        _binding = null
    }

}