package com.example.aplicacionexamen

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import android.app.DatePickerDialog
import android.widget.TextView
import com.example.aplicacionexamen.GestorSQL
import java.util.Calendar
import java.util.Locale

class CrearFarmaciaActivity : AppCompatActivity() {
    private lateinit var gestorSQL: GestorSQL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_farmacia)

        val fechaAperturaTextView: TextView = findViewById(R.id.editTextFechaApertura)
        val nombreEditText = findViewById<EditText>(R.id.editTextNombre)
        val direccionEditText = findViewById<EditText>(R.id.editTextDireccion)
        val telefonoEditText = findViewById<EditText>(R.id.editTextTelefono)
        val guardarButton = findViewById<Button>(R.id.buttonGuardar)

        // Inicializar el gestorSQL
        gestorSQL = GestorSQL(this)

        // Configurar el listener para mostrar el DatePickerDialog
        fechaAperturaTextView.setOnClickListener {
            mostrarCalendario(fechaAperturaTextView)
        }

        guardarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val direccion = direccionEditText.text.toString().trim()
            val telefono = telefonoEditText.text.toString().trim()
            val fechaApertura = fechaAperturaTextView.text.toString().trim()

            // Validar los campos antes de guardar
            if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || fechaApertura.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar formato de la fecha
            val formatoAlmacenamiento = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatoVisual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fechaAlmacenamiento: String
            try {
                // Convertir la fecha seleccionada al formato de almacenamiento
                val fechaParseada = formatoVisual.parse(fechaApertura)
                fechaAlmacenamiento = formatoAlmacenamiento.format(fechaParseada!!)
            } catch (e: Exception) {
                Toast.makeText(this, "Error: Fecha inválida, usa formato dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Intentar guardar la farmacia
                val id = gestorSQL.addFarmacia(nombre, direccion, telefono, fechaAlmacenamiento)
                if (id > 0) {
                    Log.d("CrearFarmaciaActivity", "Farmacia creada con ID: $id")
                    Toast.makeText(this, "Farmacia creada con éxito", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Log.e("CrearFarmaciaActivity", "Error al guardar la farmacia")
                    Toast.makeText(this, "Error al guardar la farmacia", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_CANCELED)
                }
            } catch (e: Exception) {
                Log.e("CrearFarmaciaActivity", "Error: ${e.message}")
                Toast.makeText(this, "Ocurrió un error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarCalendario(fechaTextView: TextView) {
        // Obtener la fecha actual
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        // Crear el DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Formatear la fecha seleccionada
                val fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.set(year, month, dayOfMonth)
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                fechaTextView.text = formatoFecha.format(fechaSeleccionada.time)
            },
            anio,
            mes,
            dia
        )

        // Mostrar el calendario
        datePickerDialog.show()
    }
}
