package fr.isen.boillot.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import fr.isen.boillot.androiderestaurant.databinding.ActivityHomeBinding

private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var optionSelected: Button;
        binding.buttonStarter.setOnClickListener {
            Toast.makeText(applicationContext, "Choisissez votre entrée", Toast.LENGTH_LONG).show()
            val intent = Intent(this@HomeActivity, ListActivity::class.java)
            optionSelected = findViewById(R.id.button_starter)
            val message = optionSelected.text.toString()
            intent.putExtra("message_key", message)
            startActivity(intent)
        }
        binding.buttonMain.setOnClickListener {
            Toast.makeText(applicationContext, "Choisissez votre plat", Toast.LENGTH_LONG).show()
            val intent = Intent(this@HomeActivity, ListActivity::class.java)
            optionSelected = findViewById(R.id.button_main)
            val message = optionSelected.text.toString()
            intent.putExtra("message_key", message)
            startActivity(intent)
        }
        binding.buttonDessert.setOnClickListener {
            Toast.makeText(applicationContext, "Choisissez votre dessert", Toast.LENGTH_LONG).show()
            val intent = Intent(this@HomeActivity, ListActivity::class.java)
            optionSelected = findViewById(R.id.button_dessert)
            val category = optionSelected.text.toString()
            intent.putExtra("category_key", category)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("HomeActivity", "onDestroy Called")
    }

}