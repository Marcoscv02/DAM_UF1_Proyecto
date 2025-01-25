import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.PeakFlow.databinding.FragmentCreateRouteBinding
import com.example.UF1_Proyecto.data.database.PeakFlowDataBase
import com.example.UF1_Proyecto.data.database.dao.RouteDao
import com.example.UF1_Proyecto.data.database.entities.RouteEntity
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Fragmento para crear y gestionar rutas.
 * Este fragmento permite a los usuarios iniciar/detener la grabación de rutas y guardarlas.
 */
class CreateRouteFragment : Fragment() {

    /**
     * Objeto de enlace (binding) anulable para el diseño del fragmento.
     */
    private var _binding: FragmentCreateRouteBinding? = null
    private val binding get() = _binding!!

    private lateinit var map:GoogleMap

    //Indica si la grabación de la ruta está en progreso.
    private var isRecording = false

    // Contador para el tiempo de grabación
    private var elapsedTime: Long = 0L
    private var handler: Handler? = null
    private lateinit var routeDao:RouteDao


    //Se llama para que el fragmento instancie su vista de interfaz de usuario.
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
        // Inicializa routeDao
        routeDao = PeakFlowDataBase.getDatabase(requireContext()).routeDao()


        initializeMapView() // Llama a la función para inicializar el mapa
        setupButtons()
        updateUI()
    }

    /**
     * Configura las opciones del mapa como zoom, controles y posición inicial.
     */
    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true // Habilita los controles de zoom
        map.uiSettings.isMyLocationButtonEnabled = true // Habilita el botón de ubicación
        map.setMinZoomPreference(10f) // Establece un zoom mínimo

        // Configura la posición inicial (opcional)
        val initialLocation = com.google.android.gms.maps.model.LatLng(42.88052, -8.54569) // Madrid
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
     * Inicializa todas las variables del fragmento a cero.
     */
    private fun initializeVariables() {
        elapsedTime = 0L
        updateUI()
    }

    /**
     * Actualiza las vistas y campos con la información actual.
     */
    private fun updateUI() {
        binding.routeTime.setText(formatElapsedTime(elapsedTime))
        binding.routeTime.isFocusable = false
        binding.distancia.setText("0.0")
        binding.time.setText("0.0")
        binding.distance.setText("0.0")
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
            saveRoute()
        }
    }

    /**
     * Actualiza el estado de la IU de los botones según si se está grabando una ruta o no.
     */
    private fun updateButtonStates() {
        binding.btnStartRoute.setImageResource(
            if (isRecording) android.R.drawable.ic_media_pause
            else android.R.drawable.ic_media_play
        )
        binding.btnSaveRoute.isEnabled = !isRecording
    }



    private fun showRouteFinishedMessage() {
        val snackbar = Snackbar.make(binding.root, "Se ha pausado el seguimiento de la ruta", Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()
    }

    /**
     * Comienza la grabación de la ruta.
     */
    private fun startRecording() {
        isRecording = true
        handler = Handler(Looper.getMainLooper(), Handler.Callback {
            if (it.what == 1) {
                elapsedTime += 1000L
                binding.routeTime.setText(formatElapsedTime(elapsedTime))
                handler?.sendEmptyMessageDelayed(1, 1000)
            }
            true
        })
        handler?.sendEmptyMessage(1)
    }

    /**
     * detiene la grabación de la ruta.
     */
    private fun stopRecording() {
        isRecording = false
        handler?.removeMessages(1)
    }

    /**
     * Guarda la ruta actual en la base de datos.
     */
    private fun saveRoute() {
        val routeName = binding.etRouteName.text.toString().trim()
        val elapsedTimeInMillis = elapsedTime
        val distance = binding.distance.getText().toString().toDouble()
        val avgSpeed = binding.time.getText().toString().toDouble()
        val userId = getCurrentUserId() // Implementar lógica para obtener el ID del usuario actual
        val trackDate = java.util.Date().time

        val newRoute = RouteEntity(
            nombreRuta = routeName,
            tiempoRuta = elapsedTimeInMillis,
            velMedia = avgSpeed,
            distancia = distance,
            fechaRealizacion = trackDate,
            idUsuario = userId
        )


        // Usa lifecycleScope para manejar operaciones asincrónicas de Room
        lifecycleScope.launch {

            val routeId = routeDao.createRoute(newRoute)
            showRouteSavedMessage(routeId)
        }
    }

    /**
     * Muestra un mensaje cuando se guarda la ruta correctamente.
     */
    private fun showRouteSavedMessage(routeId: Long) {
        val message = "Ruta guardada correctamente con ID: $routeId"
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * Devuelve el ID del usuario actual.
     * Reemplaza este método con la lógica para obtener el ID del usuario.
     */
    private fun getCurrentUserId(): Int {
        return 1 // REEMPLAZAR POR LA LOGICA CORRECTA
    }


    /**
     * Formatea el tiempo en milisegundos a formato "HH:mm:ss".
     */
    @SuppressLint("DefaultLocale")
    private fun formatElapsedTime(timeInMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
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
