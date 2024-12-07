package service

import model.Farmacia
import model.Medicamento
import utils.FileUtils

class MedicamentoService {
    private val filePath = "C:\\Users\\eddya\\OneDrive\\Escritorio\\mov-sw-gr1-arias-sarango-eddy-santiago\\Examen_01_bim\\src\\main\\kotlin\\data\\medicamentos.txt"
    private val filePathFarmacias = "C:\\Users\\eddya\\OneDrive\\Escritorio\\mov-sw-gr1-arias-sarango-eddy-santiago\\Examen_01_bim\\src\\main\\kotlin\\data\\farmacias.txt"


    fun crearMedicamento() {
        println("\n--- Crear Medicamento ---")
        println("Ingrese ID del medicamento:")
        val id = readln().toInt()
        println("Ingrese nombre del medicamento:")
        val nombre = readln()
        println("Ingrese dosis (en mg):")
        val dosis = readln().toDouble()
        println("¿Requiere receta? (true/false):")
        val receta = readln().toBoolean()
        println("Ingrese cantidad en stock:")
        val cantidad = readln().toInt()
        println("Ingrese laboratorio:")
        val laboratorio = readln()

        // Mostrar la lista de farmacias para que el usuario seleccione
        println("\n--- Seleccionar Farmacia ---")
        val farmacias = FileUtils.leerFarmaciasDesdeArchivo(filePathFarmacias)
        if (farmacias.isNotEmpty()) {
            farmacias.forEachIndexed { index, farmacia ->
                println("${index + 1}. ${farmacia.nombre} (ID: ${farmacia.id})")
            }
            println("Seleccione el número de la farmacia:")
            val farmaciaSeleccionada = readln().toInt() - 1
            if (farmaciaSeleccionada in farmacias.indices) {
                val farmaciaId = farmacias[farmaciaSeleccionada].id

                // Crear medicamento y asociar a la farmacia seleccionada
                val medicamento = Medicamento(id, nombre, dosis, receta, cantidad, laboratorio, farmaciaId)
                val medicamentos = leerMedicamentosDesdeArchivo()
                medicamentos.add(medicamento)
                guardarMedicamentosEnArchivo(medicamentos)
                println("¡Medicamento creado y asociado a ${farmacias[farmaciaSeleccionada].nombre} con éxito!")
            } else {
                println("Selección de farmacia inválida.")
            }
        } else {
            println("No hay farmacias registradas para asociar el medicamento.")
        }
    }


    fun leerMedicamentos() {
        val medicamentos = leerMedicamentosDesdeArchivo()
        medicamentos.forEach { println(it) }
    }

    fun actualizarMedicamento() {
        val medicamentos = leerMedicamentosDesdeArchivo()
        println("Ingrese ID del medicamento a actualizar:")
        val id = readln().toInt()
        val medicamento = medicamentos.find { it.id == id }
        if (medicamento != null) {
            println("Ingrese nuevo nombre (actual: ${medicamento.nombre}):")
            medicamento.nombre = readln()
            println("Ingrese nueva dosis (actual: ${medicamento.dosis}):")
            medicamento.dosis = readln().toDouble()
            guardarMedicamentosEnArchivo(medicamentos)
        } else {
            println("Medicamento no encontrado.")
        }
    }

    fun eliminarMedicamento() {
        val medicamentos = leerMedicamentosDesdeArchivo()
        println("Ingrese ID del medicamento a eliminar:")
        val id = readln().toInt()
        val nuevaLista = medicamentos.filter { it.id != id }
        guardarMedicamentosEnArchivo(nuevaLista)
    }

    private fun leerMedicamentosDesdeArchivo(): MutableList<Medicamento> {
        return FileUtils.leerMedicamentosDesdeArchivo(filePath)
    }

    private fun guardarMedicamentosEnArchivo(medicamentos: List<Medicamento>) {
        FileUtils.guardarMedicamentosEnArchivo(filePath, medicamentos)
    }
}
