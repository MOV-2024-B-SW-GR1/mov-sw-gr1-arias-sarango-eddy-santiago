package utils

import model.Farmacia
import model.Medicamento
import java.io.File

object FileUtils {

    // Leer archivo y devolver una lista de Farmacia o Medicamento dependiendo del tipo que se pase
    fun leerFarmaciasDesdeArchivo(filePath: String): MutableList<Farmacia> {
        val file = File(filePath)
        val result = mutableListOf<Farmacia>()
        if (!file.exists()) return result

        file.readLines().forEach { line ->
            val parts = line.split(",") // Asumimos que los datos están separados por comas
            if (parts.size == 5) {
                val farmacia = Farmacia(parts[0].toInt(), parts[1], parts[2], parts[3], parts[4])
                result.add(farmacia)
            }
        }
        return result
    }

    // Leer archivo y devolver una lista de Medicamento
    fun leerMedicamentosDesdeArchivo(filePath: String): MutableList<Medicamento> {
        val file = File(filePath)
        val result = mutableListOf<Medicamento>()
        if (!file.exists()) return result

        file.readLines().forEach { line ->
            val parts = line.split(",")
            if (parts.size == 7) {  // Incluimos el campo farmaciaId
                val medicamento = Medicamento(
                    parts[0].toInt(),
                    parts[1],
                    parts[2].toDouble(),
                    parts[3].toBoolean(),
                    parts[4].toInt(),
                    parts[5],
                    parts[6].toInt()  // Leemos farmaciaId desde el archivo
                )
                result.add(medicamento)
            }
        }
        return result
    }

    // Guardar lista de farmacias en el archivo
    fun guardarFarmaciasEnArchivo(filePath: String, farmacias: List<Farmacia>) {
        val file = File(filePath)
        file.bufferedWriter().use { out ->
            farmacias.forEach { farmacia ->
                out.write("${farmacia.id},${farmacia.nombre},${farmacia.direccion},${farmacia.telefono},${farmacia.fechaApertura}")
                out.newLine()
            }
        }
    }

    // Guardar lista de medicamentos en el archivo
    fun guardarMedicamentosEnArchivo(filePath: String, medicamentos: List<Medicamento>) {
        val file = File(filePath)
        file.bufferedWriter().use { out ->
            medicamentos.forEach { medicamento ->
                out.write("${medicamento.id},${medicamento.nombre},${medicamento.dosis},${medicamento.receta},${medicamento.cantidad},${medicamento.laboratorio},${medicamento.farmaciaId}")
                out.newLine()
            }
        }
    }
}
