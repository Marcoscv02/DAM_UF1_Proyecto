package com.example.UF1_Proyecto.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.UF1_Proyecto.databinding.FragmentImportRouteBinding

class ImportRouteFragment : Fragment() {

    // Declara la variable para el binding
    private var _binding: FragmentImportRouteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usa ViewBinding para inflar el layout
        _binding = FragmentImportRouteBinding.inflate(inflater, container, false)

        // Devuelve la raíz del binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Libera la referencia del binding al destruir la vista
        _binding = null
    }
}
