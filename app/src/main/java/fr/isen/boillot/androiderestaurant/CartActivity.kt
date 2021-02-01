package fr.isen.boillot.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.adapters.CartAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.boillot.androiderestaurant.model.OrderList
import java.io.File

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val file = File(cacheDir.absolutePath + "/${DetailActivity.FILE_ORDER}")
        if(file.exists()) {
            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList
            binding.recyclerViewCart.isVisible = true
            linearLayoutManager = LinearLayoutManager(this)
            binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewCart.adapter = CartAdapter(orderList)
        }
    }
}