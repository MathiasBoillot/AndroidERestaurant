package fr.isen.boillot.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import fr.isen.boillot.androiderestaurant.category.CategoryActivity
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

    /**
     * @param id to get the text of the button
     * Start CategoryActivity depend on the button clicked
     */
    private fun displayListPage(id: Button) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY, id.text.toString())
        startActivity(intent)
    }


    /**
     * Log when HomeActivity is destroy
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.i("HomeActivity", "onDestroy Called")
    }


    /**
     * Override to invalid the options menu
     */
    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }
}