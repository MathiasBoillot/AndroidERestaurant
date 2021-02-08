package fr.isen.boillot.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.boillot.androiderestaurant.databinding.ActivitySuccessOrderBinding

class SuccessOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuccessOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val price = intent.getStringExtra("price_paid")

        binding.backHomeSuccess.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.priceOrderSuccess.text = price
    }
}