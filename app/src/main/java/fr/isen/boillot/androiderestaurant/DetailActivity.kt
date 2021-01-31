package fr.isen.boillot.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.adapters.ViewPagerAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.boillot.androiderestaurant.model.Item
import fr.isen.boillot.androiderestaurant.model.Order
import fr.isen.boillot.androiderestaurant.model.OrderList
import java.io.File

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
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

        val file = File(cacheDir.absolutePath + "/saveCart.json")

        if (file.exists()) {

            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList

            orderList.order.firstOrNull { it.item == item }?.let {
                it.quantity += quantity
            } ?: run {
                orderList.order.add(Order(item, quantity))
            }
            file.writeText(Gson().toJson(orderList))
        } else {
            val jsonObject = Gson().toJson(OrderList(mutableListOf(Order(item, quantity))))
            file.writeText(jsonObject)
        }
        Snackbar.make(binding.root, "Ajouté au panier", Snackbar.LENGTH_LONG).show()
    }

    private fun calculTotal(quantity: Int, item: Item) {
        val total = quantity * item.getPrice()
        binding.totalDetail.text = "Total : $total"
    }
}