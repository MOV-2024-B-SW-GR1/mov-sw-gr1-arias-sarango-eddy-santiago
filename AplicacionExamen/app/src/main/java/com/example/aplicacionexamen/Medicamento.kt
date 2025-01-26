package com.example.aplicacionexamen

import android.os.Parcel
import android.os.Parcelable

data class Medicamento(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Float,
    var cantidad: Int,
    var idFarmacia: Int // ID de la farmacia asociada
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeFloat(precio)
        parcel.writeInt(cantidad)
        parcel.writeInt(idFarmacia)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medicamento> {
        override fun createFromParcel(parcel: Parcel): Medicamento {
            return Medicamento(parcel)
        }

        override fun newArray(size: Int): Array<Medicamento?> {
            return arrayOfNulls(size)
        }
    }
}