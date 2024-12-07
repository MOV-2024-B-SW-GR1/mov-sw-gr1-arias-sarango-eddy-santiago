package model

data class Medicamento(
    var id: Int,
    var nombre: String,
    var dosis: Double, // Decimal
    var receta: Boolean, // Booleano
    var cantidad: Int, // Entero
    var laboratorio: String, // String
    var farmaciaId: Int // ID of the associated pharmacy
)
