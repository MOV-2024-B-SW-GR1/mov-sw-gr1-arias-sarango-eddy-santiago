package service

import model.Farmacia
import utils.FileUtils
import utils.FileUtils.leerMedicamentosDesdeArchivo

class FarmaciaService {
    private val filePath = "C:\\Users\\eddya\\OneDrive\\Escritorio\\mov-sw-gr1-arias-sarango-eddy-santiago\\Examen_01_bim\\src\\main\\kotlin\\data\\farmacias.txt"
    private val filePathMedicamentos = "C:\\Users\\eddya\\OneDrive\\Escritorio\\mov-sw-gr1-arias-sarango-eddy-santiago\\Examen_01_bim\\src\\main\\kotlin\\data\\medicamentos.txt"  // Ruta del archivo de medicamentos


    fun crearFarmacia() {
        println("\n--- Crear Farmacia ---")
        println("Ingrese ID de la farmacia:")
        val id = readln().toInt()
        println("Ingrese nombre de la farmacia:")
        val nombre = readln()
        println("Ingrese dirección:")
        val direccion = readln()
        println("Ingrese teléfono:")
        val telefono = readln()
        println("Ingrese fecha de apertura (dd/MM/yyyy):")
        val fechaApertura = readln()

        val farmacia = Farmacia(id, nombre, direccion, telefono, fechaApertura)
        val farmacias = leerFarmaciasDesdeArchivo()
        farmacias.add(farmacia)
        guardarFarmaciasEnArchivo(farmacias)
        println("¡Farmacia creada con éxito!")
    }

    fun leerFarmacias() {
        println("\n--- Lista de Farmacias ---")
        val farmacias = leerFarmaciasDesdeArchivo()
        if (farmacias.isNotEmpty()) {
            farmacias.forEachIndexed { index, farmacia ->
                println(
                    "${index + 1}. ID: ${farmacia.id}, Nombre: ${farmacia.nombre}, Dirección: ${farmacia.direccion}, " +
                            "Teléfono: ${farmacia.telefono}, Fecha de Apertura: ${farmacia.fechaApertura}"
                )
            }
        } else {
            println("No hay farmacias registradas.")
        }
    }


    fun actualizarFarmacia() {
        val farmacias = leerFarmaciasDesdeArchivo()
        println("Ingrese ID de la farmacia a actualizar:")
        val id = readln().toInt()
        val farmacia = farmacias.find { it.id == id }
        if (farmacia != null) {
            println("Ingrese nuevo nombre (actual: ${farmacia.nombre}):")
            farmacia.nombre = readln()
            println("Ingrese nueva dirección (actual: ${farmacia.direccion}):")
            farmacia.direccion = readln()
            println("Ingrese nuevo teléfono (actual: ${farmacia.telefono}):")
            farmacia.telefono = readln()
            guardarFarmaciasEnArchivo(farmacias)
        } else {
            println("Farmacia no encontrada.")
        }
    }

    fun eliminarFarmacia() {
        val farmacias = leerFarmaciasDesdeArchivo()
        println("Ingrese ID de la farmacia a eliminar:")
        val id = readln().toInt()
        val nuevaLista = farmacias.filter { it.id != id }
        guardarFarmaciasEnArchivo(nuevaLista)
    }

    // Función para ver todos los medicamentos de una farmacia
    fun verMedicamentosDeFarmacia() {
        println("Ingrese ID de la farmacia para ver sus medicamentos:")
        val farmaciaId = readln().toInt()

        val farmacias = leerFarmaciasDesdeArchivo()
        val farmacia = farmacias.find { it.id == farmaciaId }

        if (farmacia != null) {
            println("Medicamentos disponibles en la farmacia ${farmacia.nombre}:")
            // Filtramos los medicamentos que pertenecen a esta farmacia
            val medicamentos = leerMedicamentosDesdeArchivo(filePathMedicamentos)
            val medicamentosDeFarmacia = medicamentos.filter { it.farmaciaId == farmaciaId }
            if (medicamentosDeFarmacia.isNotEmpty()) {
                medicamentosDeFarmacia.forEach { println(it) }
            } else {
                println("No hay medicamentos disponibles en esta farmacia.")
            }
        } else {
            println("Farmacia no encontrada.")
        }
    }

    // Función para leer farmacias desde el archivo
    private fun leerFarmaciasDesdeArchivo(): MutableList<Farmacia> {
        return FileUtils.leerFarmaciasDesdeArchivo(filePath)
    }

    private fun guardarFarmaciasEnArchivo(farmacias: List<Farmacia>) {
        FileUtils.guardarFarmaciasEnArchivo(filePath, farmacias)
    }
}
