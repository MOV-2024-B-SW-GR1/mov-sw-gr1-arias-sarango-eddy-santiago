package com.example.aplicacionexamen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.aplicacionexamen.Medicamento

class EditMedicamentoDialogFragment : DialogFragment() {

    interface EditMedicamentoDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, medicamento: Medicamento)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    private lateinit var medicamento: Medicamento
    private lateinit var listener: EditMedicamentoDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        listener = activity as EditMedicamentoDialogListener
        medicamento = arguments?.getParcelable("medicamento") ?: Medicamento(0, "", "", 0.0f, 0, 0)

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_medicamento, null)

        val nombreEditText = view.findViewById<EditText>(R.id.editTextNombre)
        val descripcionEditText = view.findViewById<EditText>(R.id.editTextDescripcion)
        val precioEditText = view.findViewById<EditText>(R.id.editTextPrecio)
        val cantidadEditText = view.findViewById<EditText>(R.id.editTextCantidad)

        nombreEditText.setText(medicamento.nombre)
        descripcionEditText.setText(medicamento.descripcion)
        precioEditText.setText(medicamento.precio.toString())
        cantidadEditText.setText(medicamento.cantidad.toString())

        builder.setView(view)
            .setTitle("Editar Medicamento")
            .setPositiveButton("Guardar") { _, _ ->
                medicamento.nombre = nombreEditText.text.toString()
                medicamento.descripcion = descripcionEditText.text.toString()
                medicamento.precio = precioEditText.text.toString().toFloat()
                medicamento.cantidad = cantidadEditText.text.toString().toInt()
                listener.onDialogPositiveClick(this, medicamento)
            }
            .setNegativeButton("Cancelar") { _, _ ->
                listener.onDialogNegativeClick(this)
            }

        return builder.create()
    }

    companion object {
        fun newInstance(medicamento: Medicamento): EditMedicamentoDialogFragment {
            val dialog = EditMedicamentoDialogFragment()
            val args = Bundle()
            args.putParcelable("medicamento", medicamento)
            dialog.arguments = args
            return dialog
        }
    }
}