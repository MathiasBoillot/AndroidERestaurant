package fr.isen.boillot.androiderestaurant

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.BaseActivity.Companion.BASKET_COUNTER
import fr.isen.boillot.androiderestaurant.BaseActivity.Companion.FILE_ORDER
import fr.isen.boillot.androiderestaurant.BaseActivity.Companion.FILE_PREF
import fr.isen.boillot.androiderestaurant.BaseActivity.Companion.ID
import fr.isen.boillot.androiderestaurant.adapters.CartAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.boillot.androiderestaurant.model.Order
import fr.isen.boillot.androiderestaurant.model.OrderList
import fr.isen.boillot.androiderestaurant.model.PreviousOrderList
import fr.isen.boillot.androiderestaurant.model.UserResult
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val file = File(cacheDir.absolutePath + "/$FILE_ORDER")
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

        binding.totalPriceOrder.setOnClickListener {
            val sharedPreference = getSharedPreferences(FILE_PREF, MODE_PRIVATE)

            if (sharedPreference.getString(ID, "0") == "0") {
                startActivity(Intent(this, RegisterActivity::class.java))
            } else {
                if (file.exists()) {
                    binding.orderLoader.isVisible = true
                    binding.recyclerViewCart.isVisible = false
                    val orderList = Gson().fromJson(file.readText(), OrderList::class.java)
                    orderPost(sharedPreference, orderList, file)
                } else {
                    Toast.makeText(this, "Panier vide", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun deleteItem(order: Order) {
        val file = File(cacheDir.absolutePath + "/$FILE_ORDER")
        file.exists().let {
            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList

            orderList.order.firstOrNull { it.item == order.item }?.let {
                if (it.quantity > 1)
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
        val sharedPreference = getSharedPreferences(FILE_PREF, MODE_PRIVATE)
        sharedPreference.edit().apply {
            putInt(BASKET_COUNTER, count)
        }.apply()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.basket_menu, menu)
//        val menuView = menu?.findItem(R.id.show_basket)?.actionView
//        val countText = menuView?.findViewById(R.id.nbItems) as? TextView
//
//        countText?.isVisible = getItemsCount() > 0
//        countText?.text = getItemsCount().toString()
//
//        return true
//    }
//
//    private fun getItemsCount(): Int {
//        val sharedPreferences = getSharedPreferences(FILE_PREF, MODE_PRIVATE)
//        return sharedPreferences.getInt(BASKET_COUNTER, 0)
//    }

    private fun orderPost(sharedPreferences: SharedPreferences, orderList: OrderList, file: File) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/order"
        val id = sharedPreferences.getString(ID, "0")
        val dataPost = JSONObject().let {
            it.put("id_shop", "1")
            it.put("id_user", id)
            it.put("msg", Gson().toJson(orderList))
        }

        val request = JsonObjectRequest(Request.Method.POST, url, dataPost, {
            Log.d("response", it.toString())
            binding.orderLoader.isVisible = false
            binding.recyclerViewCart.isVisible = true

            sharedPreferences.edit().apply {
                putInt(BASKET_COUNTER, 0)
            }.apply()

            val intent = Intent(this, SuccessOrderActivity::class.java)
            intent.putExtra("price_paid", orderList.totalPriceOrder())

            file.delete()
            binding.recyclerViewCart.adapter?.notifyDataSetChanged()
            invalidateOptionsMenu()

            startActivity(intent)

        }) { error ->
            error.printStackTrace()
            Toast.makeText(this,
                "Restaurant temporairement fermé, veuillez réessayez plus tard",
                Toast.LENGTH_LONG).show()

            startActivity(Intent(this, ErrorOrderActivity::class.java))

        }
        queue.add(request)
    }




}