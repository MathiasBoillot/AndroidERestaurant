package fr.isen.boillot.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.DetailActivity.Companion.FILE_PREF
import fr.isen.boillot.androiderestaurant.adapters.CartAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.boillot.androiderestaurant.model.Order
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
        if (file.exists()) {
            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList
            binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewCart.adapter = CartAdapter(orderList, applicationContext)
            {
                deleteItem(it)
                invalidateOptionsMenu()
            }
            "Total : ${orderList.totalPriceOrder()} ".also { binding.totalPriceOrder.text = it }
        }
    }
    private fun deleteItem(order: Order) {
        val file = File(cacheDir.absolutePath + "/${DetailActivity.FILE_ORDER}")
        file.exists().let {
            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList

                orderList.order.firstOrNull { it.item == order.item }?.let {
                    if(it.quantity > 1)
                        it.quantity--
                    else
                        orderList.order.remove(it)
                }
            cartItem(orderList)
            file.writeText(Gson().toJson(orderList))
            "Total : ${orderList.totalPriceOrder()} ".also { binding.totalPriceOrder.text = it }
        }
    }
    private fun cartItem(orders: OrderList) {
        val count = orders.order.sumOf { it.quantity }
        val sharedPreference = getSharedPreferences(FILE_PREF, AppCompatActivity.MODE_PRIVATE)
        sharedPreference.edit().apply {
            putInt("quantity", count )
        }.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.basket_menu, menu)
        val menuView = menu?.findItem(R.id.show_basket)?.actionView
        val countText = menuView?.findViewById(R.id.nbItems) as? TextView

        countText?.isVisible = getItemsCount() > 0
        countText?.text = getItemsCount().toString()

        return true
    }

    private fun getItemsCount(): Int {
        val sharedPreferences = getSharedPreferences(FILE_PREF, MODE_PRIVATE)
        return sharedPreferences.getInt("quantity", 0)
    }

}