package fr.isen.boillot.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.boillot.androiderestaurant.adapters.ViewPagerAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.boillot.androiderestaurant.model.Item
import fr.isen.boillot.androiderestaurant.model.Order
import fr.isen.boillot.androiderestaurant.model.OrderList
import java.io.File

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent.getSerializableExtra("item") as Item


        binding.titleDetail.text = item.name
        binding.detailIngredient.text = item.getIngredients()

        item.getAllPictures()?.let {
            binding.viewPager.adapter = ViewPagerAdapter(this, it)
        }

        var quantity: Int = 1

        calculTotal(quantity, item)

        binding.moreDetail.setOnClickListener {
            quantity++
            binding.quantityDetail.text = quantity.toString()
            calculTotal(quantity, item)
        }

        binding.lessDetail.setOnClickListener {
            if (quantity > 0)
                quantity--
            binding.quantityDetail.text = quantity.toString()
            calculTotal(quantity, item)
        }

        binding.totalDetail.setOnClickListener {
            createOrUpdateFile(item, quantity)
        }
    }


    private fun createOrUpdateFile(item: Item, quantity: Int) {

        val file = File(cacheDir.absolutePath + "/$FILE_ORDER")
        val gson = GsonBuilder().setPrettyPrinting().create()
        if (file.exists()) {
            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList

            orderList.order.firstOrNull { it.item == item }?.let {
                it.quantity += quantity
            } ?: run {
                orderList.order.add(Order(item, quantity))
            }
            file.writeText(gson.toJson(orderList))
        } else {
            val orderList = gson.toJson(OrderList(mutableListOf(Order(item, quantity))))
            file.writeText(orderList)
        }
        val sharedPreferences: SharedPreferences = getSharedPreferences(FILE_PREF, Context.MODE_PRIVATE)
        val currentQuantity = sharedPreferences.getInt("quantity", 0)
        sharedPreferences.edit().apply {
            putInt("quantity", currentQuantity + quantity)
            putString("item", item.toString())
        }.apply()
        Snackbar.make(binding.root, "Ajouté au panier", Snackbar.LENGTH_LONG).show()
        invalidateOptionsMenu()

    }

    private fun calculTotal(quantity: Int, item: Item) {
        val total = quantity * item.getPrice()
        binding.totalDetail.text = "Total : $total"
    }


    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    companion object {
        const val FILE_ORDER = "cart.json"
        const val FILE_PREF = "app_pref"
    }
}