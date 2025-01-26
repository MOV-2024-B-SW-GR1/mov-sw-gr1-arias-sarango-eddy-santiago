package com.example.aplicacionexamen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.aplicacionexamen.GestorSQL
import com.example.aplicacionexamen.Medicamento

class MedicamentoActivity : AppCompatActivity(), EditMedicamentoDialogFragment.EditMedicamentoDialogListener {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<Medicamento>
    private val gestorSQL: GestorSQL = GestorSQL(this)

    private var idFarmacia = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamento)

        idFarmacia = intent.getIntExtra("idFarmacia", -1)
        if (idFarmacia == -1) {
            Log.e("MedicamentoActivity", "Error: No se recibió el ID de la farmacia")
            finish()
            return
        }

        listView = findViewById(R.id.listViewMedicamentos)
        adapter = object : ArrayAdapter<Medicamento>(this, R.layout.list_item_medicamento, R.id.textViewNombre, loadMedicamento()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val medicamento = getItem(position)
                val textViewNombre = view.findViewById<TextView>(R.id.textViewNombre)
                val textViewDescripcion = view.findViewById<TextView>(R.id.textViewDescripcion)
                val textViewPrecio = view.findViewById<TextView>(R.id.textViewPrecio)
                val textViewCantidad = view.findViewById<TextView>(R.id.textViewCantidad)
                val textViewIdFarmacia = view.findViewById<TextView>(R.id.textViewIdFarmacia)
                val buttonOptions = view.findViewById<Button>(R.id.buttonOptions)

                textViewNombre.text = medicamento?.nombre
                textViewDescripcion.text = "Descripción: ${medicamento?.descripcion}"
                textViewPrecio.text = "Precio: ${medicamento?.precio}"
                textViewCantidad.text = "Cantidad: ${medicamento?.cantidad}"
                textViewIdFarmacia.text = "ID Farmacia: ${medicamento?.idFarmacia}"

                buttonOptions.setOnClickListener {
                    openContextMenu(buttonOptions) // Open the context menu when the button is clicked
                }
                return view
            }
        }
        listView.adapter = adapter
        registerForContextMenu(listView)

        val crearMedicamentoButton = findViewById<Button>(R.id.crearMedicamentosButton)
        crearMedicamentoButton.setOnClickListener {
            val intent = Intent(this, CrearMedicamentoActivity::class.java)
            intent.putExtra("idFarmacia", idFarmacia)
            startActivityForResult(intent, 1)  // Use request code to identify the result
        }
    }

    override fun onResume() {
        super.onResume()
        updateListView()
    }

    private fun loadMedicamento(): MutableList<Medicamento> {
        return gestorSQL.getMedicamento(idFarmacia).toMutableList()
    }

    private fun updateListView() {
        val medicamentos = gestorSQL.getMedicamento(idFarmacia)
        adapter.clear()
        adapter.addAll(medicamentos)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.medicamento_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val medicamentos = gestorSQL.getMedicamento(idFarmacia)

        if (info.position < 0 || info.position >= medicamentos.size) {
            Log.e("MedicamentoActivity", "Error: Posición inválida en la lista de medicamentos")
            return super.onContextItemSelected(item)
        }

        val medicamentoSeleccionado = medicamentos[info.position]

        when (item.itemId) {
            R.id.edit -> {
                val dialog = EditMedicamentoDialogFragment.newInstance(medicamentoSeleccionado)
                dialog.show(supportFragmentManager, "EditMedicamentoDialog")
            }
            R.id.delete -> {
                gestorSQL.deleteMedicamento(medicamentoSeleccionado.id)
                updateListView()
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, medicamento: Medicamento) {
        gestorSQL.updateMedicamento(medicamento.id, medicamento.nombre, medicamento.descripcion, medicamento.precio.toDouble(), medicamento.cantidad)
        updateListView()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // No hacer nada
    }
}