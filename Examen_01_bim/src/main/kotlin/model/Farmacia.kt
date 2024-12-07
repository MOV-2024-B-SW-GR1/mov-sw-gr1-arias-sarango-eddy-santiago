
package model

data class Farmacia(
    var id: Int,
    var nombre: String,
    var direccion: String,
    var telefono: String,
    var fechaApertura: String, // Fecha en formato "dd/MM/yyyy"
    var medicamentos: MutableList<Medicamento> = mutableListOf() // Lista de medicamentos asociados
)
