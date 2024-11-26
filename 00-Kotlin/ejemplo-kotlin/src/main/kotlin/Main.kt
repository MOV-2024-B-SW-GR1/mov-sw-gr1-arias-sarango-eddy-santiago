package org.example

import java.util.*

fun main() {
    println("Hello World!")

    //---- Duck Typing + Variables------------
    var ejemploVariableMutable = "Eddy Arias"
    val ejemploVariableInmutable = "Soltero :C"
    println(ejemploVariableMutable.trim())

    val edadEjemplo: Int = 21

    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    val fechaNacimiento: Date = Date()

    //---- When ----------
    val estadoCivilWhen = "C"

    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }

    // -------- if - else ------------

    val esSoltero = (estadoCivilWhen == "S")
    val coqueto = if (esSoltero) "Si" else "No" // if else


    // -------------- LLAMADA DE FUNCIONES ---------------

    imprimirNombre(ejemploVariableMutable)

    var sueldo1 = calcularSueldo(10.00) //solo parametro requerido
    var sueldo2 = calcularSueldo(10.00, 15.00, 20.00) //parametro requerido y sobreescribir parametros opcionales

    //Named parameters
    var sueldo3 = calcularSueldo(10.00, bonoEspecial = 20.00) // usando el parametro bonoEspecial en la segunda posicion
    var sueldo4 = calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    println("Sueldo calculado: ${sueldo1} +${sueldo2} + ${sueldo3} + ${sueldo4}")

    // usando el parametro bonoEspecial en primera posicion
    // usando el parametro sueldo en la segunda posicion
    // usando el parametro tasa es tercera posicion
    // gracias a los parametros nombrados


    // -------------------CLASES USO-----------------------
    //4 instancias usando todos los constructores
    val sumaA = Suma(1,1)
    val sumaF = Suma(dosParametro = 2, unoParametro = 4)
    val sumaB = Suma(null,1)
    val sumaC = Suma(1,null)
    val sumaD = Suma(null,null)

    //Usamos la función sumar dentro de cada instancia
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()

    //Uso de component object como static
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    println("==============================CLASE 7===============================")
    println("1. Sumar un valor a todos los elementos de un arreglo")
    val numbers1 = listOf(1, 2, 3, 4, 5)
    val updatedNumbers = numbers1.map { it + 15 }
    println("Original: $numbers1")
    println("Updated: $updatedNumbers")

    println("\n\n\n2. Uso del operador FILTER")
    val numbers2 = (1..12).toList()
    val greaterThanFive = numbers2.filter { it > 5 }
    val lessThanOrEqualToFive = numbers2.filter { it <= 5 }

    println("Original: $numbers2")
    println("Greater than 5: $greaterThanFive")
    println("Less than or equal to 5: $lessThanOrEqualToFive")

    println("\n\n\n3. Uso de los operadores ANY y ALL")
    val numbers3 = listOf(3, 7, 9, 2, 5)

    val anyGreaterThanFive = numbers3.any { it > 5 }
    val allGreaterThanFive = numbers3.all { it > 5 }

    println("Any number greater than 5? $anyGreaterThanFive") // True
    println("Are all numbers greater than 5? $allGreaterThanFive") // False
}


//-------------FUNCIONES----------

fun imprimirNombre(nombre:String):Unit{
    fun otraFuncionAdentro(){
        print("Otra funcion adentro")
    }

    println("Nombre: $nombre") //Template Strings
    println("Nombre: ${nombre}") //Template Strings
    println("Nombre: ${nombre + nombre}") //Uso con llaves (concatenado)
    println("Nombre: ${nombre.uppercase()}") //Uso con llaves (funcion)
    println("Nombre: $nombre.uppercase()") //INCORRECTO!
    //No puedo usar sin llaves
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null //Opcional (nullable)
    // Variable? - "?" Es Nullable (quiere decir que algun momento puede se nulo)
): Double{
    // Into -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    return if(bonoEspecial == null){
        sueldo * (100/tasa)
    }else{
        sueldo * (100/tasa) * bonoEspecial
    }
}



//------------ CLASES --------------

//Clase normal de java
abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(uno:Int,dos:Int){
        this.numeroUno = uno
        this.numeroDos = dos
    }
}


//-------Kotlin Classes


//Clase Padre
abstract class Numeros( //Constructor Primario
    //Caso 1) Parametro normal
    //uno:Int, (parametro (sin modificador acceso))

    //Caso 2) Parámetro y propiedad (atributo) (protected)
    // private var uno: Int (propiedad "instancia.uno")
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    parámetroNoUsadoNoPropiedadDeLaClase: Int? = null
){
    init {
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

//Clase Hijo
//Constructor Primario
//Clase padre, Numeros (extendiendo)     ---> Pasamos los atributos de Suma al padre Números
class Suma(unoParametro: Int,dosParametro: Int,): Numeros(unoParametro, dosParametro
){
    //Modificadores de Acceso
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito: String = "Publico implicito"

    init{ //Bloque constructor primario
        this.numeroUno //Heredamos del Padre
        this.numeroDos
        numeroUno //this. OPCIONAL (propiedades, metodos)
        numeroDos //this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito
    }

    //-----------Constructores Secundarios
    constructor(
        uno: Int?, //Entero nullable
        dos: Int,
    ):this(
        if(uno == null) 0 else uno,
        dos
    ){
        //OPCIONAL
        //Bloque de código de constructor secundario
    }

    constructor(
        uno: Int,
        dos: Int?, //Entero nullable
    ):this(
        uno,
        if(dos==null) 0 else dos,
    )

    constructor(
        uno: Int?,//Entero nullable
        dos: Int?,//Entero nullable
    ):this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos
    )


    fun sumar():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{ //Comparte entre todas las instancias, similar al STATIC
        //funciones, variables
        //NombreClase.metodo, NombreClase.funcion =>
        //Suma.pi
        val pi = 3.14
        //Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{ return num*num}
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}