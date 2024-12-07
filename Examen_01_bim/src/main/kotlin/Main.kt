package org.example

import service.FarmaciaService
import service.MedicamentoService

fun main() {
    val farmaciaService = FarmaciaService()
    val medicamentoService = MedicamentoService()

    while (true) {
        println("\n--- Menú Principal ---")
        println("1. Crear Farmacia")
        println("2. Ver Todas las Farmacias")
        println("3. Actualizar Farmacia")
        println("4. Eliminar Farmacia")
        println("5. Crear Medicamento")
        println("6. Ver Todos los Medicamentos")
        println("7. Actualizar Medicamento")
        println("8. Eliminar Medicamento")
        println("9. Ver Medicamentos de una Farmacia")
        println("10. Salir")
        print("Seleccione una opción (1-10): ")

        when (readln().toInt()) {
            1 -> farmaciaService.crearFarmacia()
            2 -> farmaciaService.leerFarmacias()
            3 -> farmaciaService.actualizarFarmacia()
            4 -> farmaciaService.eliminarFarmacia()
            5 -> medicamentoService.crearMedicamento()
            6 -> medicamentoService.leerMedicamentos()
            7 -> medicamentoService.actualizarMedicamento()
            8 -> medicamentoService.eliminarMedicamento()
            9 -> farmaciaService.verMedicamentosDeFarmacia()
            10 -> {
                println("¡Gracias por usar el sistema! Hasta luego.")
                break
            }
            else -> println("Opción inválida, por favor ingrese un número entre 1 y 10.")
        }
    }
}
