import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.UF1_Proyecto.databinding.FragmentCreateRouteBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.snackbar.Snackbar

/**
 * Fragmento para crear y gestionar rutas.
 * Este fragmento permite a los usuarios iniciar/detener la grabación de rutas y guardarlas.
 */
class CreateRouteFragment : Fragment() {

    /**
     * Objeto de enlace (binding) anulable para el diseño del fragmento.
     */
    private var _binding: FragmentCreateRouteBinding? = null
    /**
     * Acceso al objeto de enlace no nulo.
     * Esta propiedad solo es válida entre onCreateView y onDestroyView.
     */
    private val binding get() = _binding!!

    private lateinit var map:GoogleMap

    /**
     * Bandera para indicar si la grabación de la ruta está en progreso.
     */
    private var isRecording = false



    /**
     * Se llama para que el fragmento instancie su vista de interfaz de usuario.
     *
     * @param inflater El objeto LayoutInflater que se puede usar para inflar vistas.
     * @param container Si no es nulo, esta es la vista padre a la que se debe adjuntar la IU del fragmento.
     * @param savedInstanceState Si no es nulo, este fragmento está siendo reconstruido a partir de un estado guardado anterior.
     * @return Devuelve la Vista para la IU del fragmento, o nulo.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Se llama inmediatamente después de que onCreateView() haya retornado, pero antes de que se haya restaurado cualquier estado guardado en la vista.
     *
     * @param view La Vista devuelta por onCreateView().
     * @param savedInstanceState Si no es nulo, este fragmento está siendo reconstruido a partir de un estado guardado anterior.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMapView() // Llama a la función para inicializar el mapa
        setupButtons()
    }

    /**
     * Configura los listeners de clic para los botones e inicializa sus estados.
     */
    private fun setupButtons() {
        binding.btnStartRoute.setOnClickListener {
            if (isRecording) {
                stopRecording()
                showRouteFinishedMessage()
            } else {
                startRecording()
            }
            updateButtonStates()
        }

        binding.btnSaveRoute.setOnClickListener {
            // Implementar lógica para guardar la ruta
        }
    }

    private fun showRouteFinishedMessage() {
        val snackbar = Snackbar.make(binding.root, "Se ha finalizado el seguimiento de la ruta", Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()
    }

    private fun startRecording() {
        isRecording = true
        // Añadir aquí la lógica para iniciar la grabación de la ruta
    }

    private fun stopRecording() {
        isRecording = false
        // Añadir aquí la lógica para detener la grabación de la ruta
    }

    /**
     * Actualiza el estado de la IU de los botones según si se está grabando una ruta o no.
     */
    private fun updateButtonStates() {
        binding.btnStartRoute.setImageResource(
            if (isRecording) android.R.drawable.picture_frame
            else android.R.drawable.ic_media_play
        )
        binding.btnSaveRoute.isEnabled = !isRecording
    }

    /**
     * Configura las opciones del mapa como zoom, controles y posición inicial.
     */
    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true // Habilita los controles de zoom
        map.uiSettings.isMyLocationButtonEnabled = true // Habilita el botón de ubicación
        map.setMinZoomPreference(10f) // Establece un zoom mínimo

        // Configura la posición inicial (opcional)
        val initialLocation = com.google.android.gms.maps.model.LatLng(40.416775, -3.703790) // Madrid
        map.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(initialLocation, 15f))
    }


    /**
     * Inicializa y configura el MapView para mostrar el mapa.
     */
    private fun initializeMapView() {
        binding.mapView.onCreate(null) // Inicializa el MapView
        binding.mapView.getMapAsync { googleMap ->
            map = googleMap
            configureMap() // Llama a otra función para configurar el mapa
        }
    }

    /**
     * Se llama cuando la vista creada previamente por onCreateView() ha sido separada del fragmento.
     * Esto se llama después de onStop() y antes de onDestroy().
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
