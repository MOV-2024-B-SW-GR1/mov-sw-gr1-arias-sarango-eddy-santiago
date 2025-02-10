package com.example.aplicacionexamen

import android.content.Intent
import android.net.Uri
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
import com.example.aplicacionexamen.Farmacia

class MainActivity : AppCompatActivity(), EditFarmaciaDialogFragment.EditFarmaciaDialogListener {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<Farmacia>
    private val gestorSQL: GestorSQL = GestorSQL(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewFarmacias)
        adapter = object : ArrayAdapter<Farmacia>(this, R.layout.list_item_farmacia, R.id.textViewNombre, loadFarmacia()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val farmacia = getItem(position)
                val textViewNombre = view.findViewById<TextView>(R.id.textViewNombre)
                val textViewDireccion = view.findViewById<TextView>(R.id.textViewDireccion)
                val textViewTelefono = view.findViewById<TextView>(R.id.textViewTelefono)
                val textViewFechaApertura = view.findViewById<TextView>(R.id.textViewFechaApertura)
                val buttonOptions = view.findViewById<Button>(R.id.buttonOptions)

                textViewNombre.text = farmacia?.nombre
                textViewDireccion.text = "Dirección: ${farmacia?.direccion}"
                textViewTelefono.text = "Teléfono: ${farmacia?.telefono}"
                textViewFechaApertura.text = "Fecha de Apertura: ${farmacia?.fechaApertura}"

                buttonOptions.setOnClickListener {
                    openContextMenu(buttonOptions) // Open the context menu when the button is clicked
                }
                return view
            }
        }
        listView.adapter = adapter

        val crearFarmaciaButton = findViewById<Button>(R.id.crearFarmaciaButton)
        crearFarmaciaButton.setOnClickListener {
            val intent = Intent(this, CrearFarmaciaActivity::class.java)
            startActivityForResult(intent, 1)
        }

        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        updateListView()
    }

    private fun loadFarmacia(): MutableList<Farmacia> {
        return gestorSQL.getFarmacia().toMutableList()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val farmacia = gestorSQL.getFarmacia()[info.position]
        Log.d("MainActivity", "Context item selected: ${item.title}, Farmacia: ${farmacia.nombre}")

        when (item.itemId) {
            R.id.edit -> {
                Log.d("MainActivity", "Edit option selected")
                val dialog = EditFarmaciaDialogFragment.newInstance(farmacia)
                dialog.show(supportFragmentManager, "EditFarmaciaDialogFragment")
            }
            R.id.delete -> {
                Log.d("MainActivity", "Delete option selected")
                gestorSQL.deleteFarmacia(farmacia.id)
                updateListView()
            }
            R.id.view_medicamentos -> {
                Log.d("MainActivity", "View medicamentos option selected")
                viewMedicamentos(info.position)
            }
            R.id.view_location -> {
                Log.d("MainActivity", "View location selected for: ${farmacia.nombre}")
                val intent = Intent(this, GGoogleMaps::class.java).apply {
                    putExtra("LATITUD", farmacia.latitud)
                    putExtra("LONGITUD", farmacia.longitud)
                    putExtra("NOMBRE_TIENDA", farmacia.nombre)
                }
                startActivity(intent)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun updateListView() {
        val farmacias = gestorSQL.getFarmacia()
        adapter.clear()
        adapter.addAll(farmacias)
        adapter.notifyDataSetChanged()
    }

    private fun viewMedicamentos(position: Int) {
        val farmacia = gestorSQL.getFarmacia()[position]
        val intent = Intent(this, MedicamentoActivity::class.java)
        intent.putExtra("idFarmacia", farmacia.id)
        startActivity(intent)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, farmacia: Farmacia) {
        gestorSQL.updateFarmacia(farmacia.id, farmacia.nombre, farmacia.direccion, farmacia.telefono, farmacia.fechaApertura, farmacia.latitud, farmacia.longitud)
        updateListView()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // No hacer nada
    }
}