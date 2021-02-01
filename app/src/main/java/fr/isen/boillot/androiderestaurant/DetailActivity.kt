package fr.isen.boillot.androiderestaurant

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent.getSerializableExtra(ITEM) as Item


        binding.titleDetail.text = item.name
        binding.detailIngredient.text = item.getIngredients()

        item.getAllPictures()?.let {
            binding.viewPager.adapter = ViewPagerAdapter(this, it)
        }

        var quantity = 1

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
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(FILE_PREF, Context.MODE_PRIVATE)
        val currentQuantity = sharedPreferences.getInt(BASKET_COUNTER, 0)
        sharedPreferences.edit().apply {
            putInt(BASKET_COUNTER, currentQuantity + quantity)
            putString(ITEM, item.toString())
        }.apply()
        Snackbar.make(binding.root, "Ajouté au panier", Snackbar.LENGTH_LONG).show()
        invalidateOptionsMenu()

    }

    private fun calculTotal(quantity: Int, item: Item) {
        val total = quantity * item.getPrice()
        "Total : $total".also { binding.totalDetail.text = it }
    }


    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }


}