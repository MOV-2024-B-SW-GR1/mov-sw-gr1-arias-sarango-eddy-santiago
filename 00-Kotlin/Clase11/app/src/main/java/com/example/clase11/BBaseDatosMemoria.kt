package com.example.clase11

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1, "Eddy", "a@a.com"))
            arregloBEntrenador.add(BEntrenador(2, "Arias", "b@b.com"))
            arregloBEntrenador.add(BEntrenador(3, "Comite del pueblo", "c@c.com"))
        }
    }
}