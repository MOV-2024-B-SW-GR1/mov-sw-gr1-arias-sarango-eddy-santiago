package com.example.aplicacionexamen

import android.os.Parcel
import android.os.Parcelable

data class Farmacia(
    var id: Int,
    var nombre: String,
    var direccion: String,
    var telefono: String,
    var fechaApertura: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(direccion)
        parcel.writeString(telefono)
        parcel.writeString(fechaApertura)
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