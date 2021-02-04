package fr.isen.boillot.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.boillot.androiderestaurant.databinding.ActivityErrorOrderBinding

class ErrorOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityErrorOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityErrorOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backOrder.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}