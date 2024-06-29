package br.edu.utfpr.fuelsmart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import br.edu.utfpr.fuelsmart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchFuel1Launcher: ActivityResultLauncher<Intent>
    private lateinit var searchFuel2Launcher: ActivityResultLauncher<Intent>
    private var selectedFuel1: String? = null
    private var selectedFuel2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTooltips(binding)

        searchFuel1Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleActivityResult(result.resultCode, result.data, isFuel1 = true)
        }

        searchFuel2Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleActivityResult(result.resultCode, result.data, isFuel1 = false)
        }

        binding.btSearchFuel.setOnClickListener {
            hideKeyboard()
            compareFuel()
        }

        binding.btSearch1.setOnClickListener {
            val intent = Intent(this, ListFuelsActivity::class.java)
            searchFuel1Launcher.launch(intent)
        }

        binding.btSearch2.setOnClickListener {
            val intent = Intent(this, ListFuelsActivity::class.java)
            searchFuel2Launcher.launch(intent)
        }
    }

    private fun setupTooltips(binding: ActivityMainBinding) {
        binding.btnInfoLabel1.setOnClickListener {
            TooltipCompat.setTooltipText(binding.btnInfoLabel1, getString(R.string.info_fuels_label))
            binding.btnInfoLabel1.performLongClick()
        }

        binding.btnInfoLabel2.setOnClickListener {
            TooltipCompat.setTooltipText(binding.btnInfoLabel2, getString(R.string.info_fuel_liter_price))
            binding.btnInfoLabel2.performLongClick()
        }
    }

    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun handleActivityResult(resultCode: Int, data: Intent?, isFuel1: Boolean) {
        if (resultCode == RESULT_OK && data != null) {
            val selectedFuel = data.getStringExtra("selectedFuel")
            val consumption = data.getDoubleExtra("consumption", 0.0)
            if (isFuel1) {
                if (selectedFuel == selectedFuel2) {
                    Toast.makeText(this, getString(R.string.selected_as_fuel_2), Toast.LENGTH_SHORT).show()
                } else {
                    selectedFuel1 = selectedFuel
                    binding.tietFuel1.setText(consumption.toString())
                }
            } else {
                if (selectedFuel == selectedFuel1) {
                    Toast.makeText(this, getString(R.string.selected_as_fuel_1), Toast.LENGTH_SHORT).show()
                } else {
                    selectedFuel2 = selectedFuel
                    binding.tietFuel2.setText(consumption.toString())
                }
            }
        }
    }

    private fun compareFuel() {
        val consumption1 = binding.tietFuel1.text.toString().toDoubleOrNull()
        val consumption2 = binding.tietFuel2.text.toString().toDoubleOrNull()
        val price1 = binding.tietValueLiter1.text.toString().toDoubleOrNull()
        val price2 = binding.tietValueLiter2.text.toString().toDoubleOrNull()

        if (consumption1 == null || consumption2 == null || price1 == null || price2 == null) {
            Toast.makeText(this, getString(R.string.toast_blank_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val costPerKm1 = price1 / consumption1
        val costPerKm2 = price2 / consumption2

        val result = if (costPerKm1 < costPerKm2) {
            getString(R.string.fuel_1_more_profitable)
        } else {
            getString(R.string.fuel_2_more_profitable)
        }

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }
}
