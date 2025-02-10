package com.example.aplicacionexamen

import android.os.Parcel
import android.os.Parcelable

data class Farmacia(
    var id: Int,
    var nombre: String,
    var direccion: String,
    var telefono: String,
    var fechaApertura: String,
    var latitud: Double,
    var longitud: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(direccion)
        parcel.writeString(telefono)
        parcel.writeString(fechaApertura)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Farmacia> {
        override fun createFromParcel(parcel: Parcel): Farmacia {
            return Farmacia(parcel)
        }

        override fun newArray(size: Int): Array<Farmacia?> {
            return arrayOfNulls(size)
        }
    }
}