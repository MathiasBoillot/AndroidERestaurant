package fr.isen.boillot.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import fr.isen.boillot.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStarter.setOnClickListener {
            Toast.makeText(applicationContext, "Choisissez votre entrée", Toast.LENGTH_LONG).show()
            displayListPage(binding.buttonStarter)
        }
        binding.buttonMain.setOnClickListener {
            Toast.makeText(applicationContext, "Choisissez votre plat", Toast.LENGTH_LONG).show()
            displayListPage(binding.buttonMain)
        }
        binding.buttonDessert.setOnClickListener {
            Toast.makeText(applicationContext, "Choisissez votre dessert", Toast.LENGTH_LONG).show()
            displayListPage(binding.buttonDessert)
        }
    }

    private fun displayListPage(id: Button) {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra(CATEGORY, id.text.toString())
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("HomeActivity", "onDestroy Called")
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }
}