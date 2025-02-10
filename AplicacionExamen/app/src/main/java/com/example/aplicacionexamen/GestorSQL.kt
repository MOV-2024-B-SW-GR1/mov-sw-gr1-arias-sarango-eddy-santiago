package com.example.aplicacionexamen
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.aplicacionexamen.Farmacia
import com.example.aplicacionexamen.Medicamento

class GestorSQL(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FARMACIA = """
            CREATE TABLE Farmacia (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                direccion TEXT,
                telefono TEXT,
                fechaApertura DATE,
                latitud REAL,
                longitud REAL
            )
        """

        private const val SQL_CREATE_TABLE_MEDICAMENTO = """
            CREATE TABLE Medicamento (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                descripcion TEXT,
                precio REAL,
                cantidad INTEGER,
                farmaciaId INTEGER,
                FOREIGN KEY(farmaciaId) REFERENCES Farmacia(id)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(SQL_CREATE_TABLE_FARMACIA)
            db.execSQL(SQL_CREATE_TABLE_MEDICAMENTO)
            Log.d("GestorSQL", "Tablas creadas correctamente")
        } catch (e: Exception) {
            Log.e("GestorSQL", "Error al crear las tablas: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí puedes manejar las actualizaciones de la base de datos
    }

    // CRUD Operaciones para Farmacia
    fun addFarmacia(nombre: String, direccion: String, telefono: String, fechaApertura: String, latitud: Double, longitud: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("direccion", direccion)
            put("telefono", telefono)
            put("fechaApertura", fechaApertura)
            put("latitud", latitud)
            put("longitud", longitud)
        }
        val id = db.insert("Farmacia", null, values)

        if (id == -1L) {
            Log.e("GestorSQL", "Error al insertar la farmacia en la base de datos")
        } else {
            Log.d("GestorSQL", "Farmacia insertada correctamente con ID: $id")
        }

        return id
    }

    fun getFarmacia(): MutableList<Farmacia> {
        val db = this.readableDatabase
        val projection = arrayOf("id", "nombre", "direccion", "telefono", "fechaApertura", "latitud", "longitud")
        val cursor = db.query("Farmacia", projection, null, null, null, null, null)
        val farmacias = mutableListOf<Farmacia>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val nombre = getString(getColumnIndexOrThrow("nombre"))
                val direccion = getString(getColumnIndexOrThrow("direccion"))
                val telefono = getString(getColumnIndexOrThrow("telefono"))
                val fechaApertura = getString(getColumnIndexOrThrow("fechaApertura"))
                val latitud = getDouble(getColumnIndexOrThrow("latitud"))
                val longitud = getDouble(getColumnIndexOrThrow("longitud"))
                farmacias.add(Farmacia(id, nombre, direccion, telefono, fechaApertura, latitud, longitud))
            }
        }
        cursor.close()
        return farmacias
    }

    fun updateFarmacia(id: Int, nombre: String, direccion: String, telefono: String, fechaApertura: String, latitud: Double, longitud: Double): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("direccion", direccion)
            put("telefono", telefono)
            put("fechaApertura", fechaApertura)
            put("latitud", latitud)
            put("longitud", longitud)
        }
        return db.update("Farmacia", values, "id=?", arrayOf(id.toString()))
    }

    fun deleteFarmacia(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("Farmacia", "id=?", arrayOf(id.toString()))
    }

    // CRUD Operaciones para Medicamento
    fun addMedicamento(nombre: String, descripcion: String, precio: Double, cantidad: Int, idFarmacia: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
            put("precio", precio)
            put("cantidad", cantidad)
            put("farmaciaId", idFarmacia)
        }
        val id = db.insert("Medicamento", null, values)

        if (id == -1L) {
            Log.e("GestorSQL", "Error al insertar el medicamento en la base de datos")
        } else {
            Log.d("GestorSQL", "Medicamento insertado correctamente con ID: $id")
        }

        return id
    }

    fun getMedicamento(idFarmacia: Int): MutableList<Medicamento> {
        val db = this.readableDatabase
        val projection = arrayOf("id", "nombre", "descripcion", "precio", "cantidad", "farmaciaId")
        val cursor = db.query("Medicamento", projection, "farmaciaId = ?", arrayOf(idFarmacia.toString()), null, null, null)
        val medicamentos = mutableListOf<Medicamento>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val nombre = getString(getColumnIndexOrThrow("nombre"))
                val descripcion = getString(getColumnIndexOrThrow("descripcion"))
                val precio = getFloat(getColumnIndexOrThrow("precio"))
                val cantidad = getInt(getColumnIndexOrThrow("cantidad"))
                val farmaciaId = getInt(getColumnIndexOrThrow("farmaciaId"))

                medicamentos.add(Medicamento(id, nombre, descripcion, precio, cantidad, farmaciaId))
            }
        }
        cursor.close()
        return medicamentos
    }

    fun updateMedicamento(
        id: Int,
        nombre: String,
        descripcion: String,
        precio: Double,
        cantidad: Int,
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
            put("precio", precio)
            put("cantidad", cantidad)
        }
        return db.update("Medicamento", values, "id=?", arrayOf(id.toString()))
    }

    fun deleteMedicamento(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("Medicamento", "id=?", arrayOf(id.toString()))
    }
}
