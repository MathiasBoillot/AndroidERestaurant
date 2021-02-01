package fr.isen.boillot.androiderestaurant

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.boillot.androiderestaurant.DetailActivity.Companion.FILE_PREF
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
        if (file.exists()) {
            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList
            binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewCart.adapter = CartAdapter(orderList, applicationContext)
            {
                invalidateOptionsMenu()
            }
//            {
//                resetCart(orderList)
//                if(it.quantity == 1)
//                    orderList.order.remove(it)
//            }
            "Total : ${orderList.totalPriceOrder()} ".also { binding.totalPriceOrder.text = it }
        }
    }
//
//    private fun resetCart(orders: OrderList) {
//        val file = File(cacheDir.absolutePath + "/${DetailActivity.FILE_ORDER}")
//        saveInMemory(orders, file)
//    }
//
//    private fun saveInMemory(orders: OrderList, file: File) {
//        saveDishCount(orders)
//        file.writeText(Gson().toJson(orders))
//    }
//
//    private fun saveDishCount(orders: OrderList) {
//        val count = orders.order.sumOf { it.quantity }
//        val sharedPreferences: SharedPreferences = getSharedPreferences(FILE_PREF, MODE_PRIVATE)
//        sharedPreferences.edit().putInt("quantity", count).apply()
//    }

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