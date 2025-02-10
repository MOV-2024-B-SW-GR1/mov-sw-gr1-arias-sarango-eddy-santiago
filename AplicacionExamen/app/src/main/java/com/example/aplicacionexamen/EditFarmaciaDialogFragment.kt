package com.example.aplicacionexamen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class EditFarmaciaDialogFragment : DialogFragment() {

    interface EditFarmaciaDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, farmacia: Farmacia)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    private lateinit var farmacia: Farmacia
    private lateinit var listener: EditFarmaciaDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        listener = activity as EditFarmaciaDialogListener
        farmacia = arguments?.getParcelable<Farmacia>("farmacia") ?: Farmacia(0, "", "", "", "", 0.0, 0.0)

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_farmacia, null)

        val nombreEditText = view.findViewById<EditText>(R.id.editTextNombre)
        val direccionEditText = view.findViewById<EditText>(R.id.editTextDireccion)
        val telefonoEditText = view.findViewById<EditText>(R.id.editTextTelefono)
        val fechaAperturaEditText = view.findViewById<EditText>(R.id.editTextFechaApertura)
        val latitudEditText = view.findViewById<EditText>(R.id.editTextLatitud)
        val longitudEditText = view.findViewById<EditText>(R.id.editTextLongitud)

        nombreEditText.setText(farmacia.nombre)
        direccionEditText.setText(farmacia.direccion)
        telefonoEditText.setText(farmacia.telefono)
        fechaAperturaEditText.setText(farmacia.fechaApertura)
        latitudEditText.setText(farmacia.latitud.toString())
        longitudEditText.setText(farmacia.longitud.toString())

        builder.setView(view)
            .setTitle("Editar Farmacia")
            .setPositiveButton("Guardar") { _, _ ->
                farmacia.nombre = nombreEditText.text.toString()
                farmacia.direccion = direccionEditText.text.toString()
                farmacia.telefono = telefonoEditText.text.toString()
                farmacia.fechaApertura = fechaAperturaEditText.text.toString()
                farmacia.latitud = latitudEditText.text.toString().toDoubleOrNull() ?: 0.0
                farmacia.longitud = longitudEditText.text.toString().toDoubleOrNull() ?: 0.0
                listener.onDialogPositiveClick(this, farmacia)
            }
            .setNegativeButton("Cancelar") { _, _ ->
                listener.onDialogNegativeClick(this)
            }

        return builder.create()
    }

    companion object {
        fun newInstance(farmacia: Farmacia): EditFarmaciaDialogFragment {
            val dialog = EditFarmaciaDialogFragment()
            val args = Bundle()
            args.putParcelable("farmacia", farmacia)
            dialog.arguments = args
            return dialog
        }
    }
}