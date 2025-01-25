package com.example.UF1_Proyecto.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.PeakFlow.databinding.FragmentRouteDetailBinding

class RouteDetailFragment : Fragment() {

    // Declara una variable para el binding
    private var _binding: FragmentRouteDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usa ViewBinding para inflar el layout
        _binding = FragmentRouteDetailBinding.inflate(inflater, container, false)

        // Devuelve la raíz del binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Limpia la referencia al binding al destruir la vista
        _binding = null
    }
}
