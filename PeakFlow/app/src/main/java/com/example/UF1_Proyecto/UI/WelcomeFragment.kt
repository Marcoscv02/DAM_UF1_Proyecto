package com.example.UF1_Proyecto.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.UF1_Proyecto.R
import com.example.UF1_Proyecto.databinding.FragmentWelcomeBinding

/**
 * Fragmento que muestra la pantalla de bienvenida y gestiona la navegación al fragmento de la lista de rutas.
 * Este fragmento contiene un botón que al hacer clic navega a la pantalla siguiente de la aplicación.
 */
class WelcomeFragment : Fragment() {

    // Variable privada para el binding, que se inicializa solo una vez que la vista es inflada
    private var _binding: FragmentWelcomeBinding? = null
    // Propiedad que devuelve el binding no nulo
    private val binding get() = _binding!!

    /**
     * Método que se llama cuando se crea la vista del fragmento.
     * Infla el layout y asigna los eventos de la interfaz de usuario.
     *
     * @param inflater El inflador de la vista para inflar el layout del fragmento.
     * @param container El contenedor padre que contiene el fragmento.
     * @param savedInstanceState Estado guardado del fragmento si existe.
     * @return La vista inflada del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para el fragmento utilizando ViewBinding
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        // Obtiene la raíz de la vista del binding
        val view = binding.root

        // Asigna un evento al botón para navegar a la siguiente pantalla
        binding.btnLogin.setOnClickListener {
            // Navega al fragmento de la lista de rutas cuando se hace clic en el botón
            view.findNavController().navigate(R.id.action_welcomeFragment_to_routeListFragment)
        }

        return view
    }

    /**
     * Método que se llama cuando la vista del fragmento es destruida.
     * Libera el objeto de binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Libera la referencia al binding para evitar fugas de memoria
        _binding = null
    }
}
