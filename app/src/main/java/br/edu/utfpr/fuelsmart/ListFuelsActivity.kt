package br.edu.utfpr.fuelsmart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListFuelsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_fuels)

        val listView: ListView = findViewById(R.id.list_fuel)
        val gasoline = getString(R.string.gasoline)
        val ethanol = getString(R.string.ethanol)
        val diesel = getString(R.string.diesel)

        val fuelConsumption = mapOf(
            gasoline to 14.2,
            ethanol to 11.4,
            diesel to 16.5
        )
        val fuels = fuelConsumption.keys.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fuels)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedFuel = fuels[position]
            val consumption = fuelConsumption[selectedFuel]
            val resultIntent = Intent()
            resultIntent.putExtra("selectedFuel", selectedFuel)
            resultIntent.putExtra("consumption", consumption)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
