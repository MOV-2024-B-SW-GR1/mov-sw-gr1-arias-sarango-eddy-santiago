package com.example.aplicacionexamen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionexamen.GestorSQL

class CrearMedicamentoActivity : AppCompatActivity() {
    private lateinit var gestorSQL: GestorSQL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_medicamento)

        val nombreEditText = findViewById<EditText>(R.id.editTextNombre)
        val descripcionEditText = findViewById<EditText>(R.id.editTextDescripcion)
        val precioEditText = findViewById<EditText>(R.id.editTextPrecio)
        val cantidadEditText = findViewById<EditText>(R.id.editTextCantidad)
        val guardarMedicamentoButton = findViewById<Button>(R.id.buttonGuardar)

        gestorSQL = GestorSQL(this)

        val idFarmacia = intent.getIntExtra("idFarmacia", -1)
        if (idFarmacia == -1) {
            Toast.makeText(this, "Error: No se recibió el ID de la farmacia", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        guardarMedicamentoButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val descripcion = descripcionEditText.text.toString().trim()
            val precioText = precioEditText.text.toString().trim()
            val cantidadText = cantidadEditText.text.toString().trim()

            val precio: Double = try {
                precioText.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Ingrese un valor numérico para el precio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cantidad: Int = try {
                cantidadText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Ingrese un valor numérico para la cantidad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            gestorSQL.addMedicamento(nombre, descripcion, precio, cantidad, idFarmacia)
            Toast.makeText(this, "Medicamento guardado exitosamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}