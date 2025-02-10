package com.example.aplicacionexamen

import android.Manifest
import android.util.Log
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GGoogleMaps : AppCompatActivity() {
    private lateinit var mapa: GoogleMap

    private val nombrePermisoFine = Manifest.permission.ACCESS_FINE_LOCATION
    private val nombrePermisoCoarse = Manifest.permission.ACCESS_COARSE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_ggoogle_maps)
    Log.d("GGoogleMaps", "Activity created")

    // Recuperar los datos enviados desde la actividad anterior
    val latitud = intent.getDoubleExtra("LATITUD", 0.0)
    val longitud = intent.getDoubleExtra("LONGITUD", 0.0)
    val nombreTienda = intent.getStringExtra("NOMBRE_TIENDA") ?: "Tienda Desconocida"
    Log.d("GGoogleMaps", "Received data - Latitud: $latitud, Longitud: $longitud, Nombre: $nombreTienda")

    solicitarPermisos()
    inicializarLogicaMapa(latitud, longitud, nombreTienda)
}

    private fun inicializarLogicaMapa(latitud: Double, longitud: Double, nombreTienda: String) {
        Log.d("GGoogleMaps", "Initializing map logic")
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            mapa = googleMap
            Log.d("GGoogleMaps", "Map is ready")
            establecerConfiguracionMapa()
            moverUbicacionTienda(latitud, longitud, nombreTienda)
        }
    }

    private fun moverUbicacionTienda(latitud: Double, longitud: Double, nombreTienda: String) {
        Log.d("GGoogleMaps", "Moving to store location: $nombreTienda at ($latitud, $longitud)")
        val ubicacionTienda = LatLng(latitud, longitud)
        val marcadorTienda = anadirMarcador(ubicacionTienda, nombreTienda)
        marcadorTienda.tag = nombreTienda
        moverCamaraConZoom(ubicacionTienda)
    }
    private fun solicitarPermisos() {
        if (!tengoPermisos()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    private fun tengoPermisos(): Boolean {
        val contexto = applicationContext
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        return permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun establecerConfiguracionMapa() {
        with(mapa) {
            if (tengoPermisos()) {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun moverCamaraConZoom(latLang: LatLng, zoom: Float = 17f) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang, zoom))
    }

    private fun anadirMarcador(latLang: LatLng, title: String): Marker {
        return mapa.addMarker(MarkerOptions().position(latLang).title(title))!!
    }
}