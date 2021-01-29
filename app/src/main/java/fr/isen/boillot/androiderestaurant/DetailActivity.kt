package fr.isen.boillot.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.adapters.ViewPagerAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.boillot.androiderestaurant.model.Item
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
        var listItem: List<String> = emptyList()

        calculTotal(quantity,item)

        binding.moreDetail.setOnClickListener {
            quantity++
            binding.quantityDetail.text = quantity.toString()
            calculTotal(quantity, item)
            createOrUpdateItem(item.name, quantity, listItem)
        }

        binding.lessDetail.setOnClickListener {
            if (quantity > 0)
                quantity--
            binding.quantityDetail.text = quantity.toString()
            calculTotal(quantity, item)
            createOrUpdateItem(item.name, quantity, listItem)
        }
    }

    private fun saveOnCart(listItem: List<String>) {
        var gson = Gson()
        var jsonString: String = gson.toJson(listItem)
    }

    private fun createOrUpdateFile(jsonString: String) {
        val file = File("saveCart")
        file.writeText(jsonString)
    }

    private fun createOrUpdateItem(name: String, quantity: Int, listItem: List<String>) {
        listItem.toMutableList().add("$name:$quantity")
        var gson = Gson()
        var jsonString: String = gson.toJson(listItem)
        val file = File("saveCart")
        file.writeText(jsonString)
        Log.i("Save file", file.readText())
    }

    private fun calculTotal(quantity: Int, item: Item) {
        val total = quantity * item.getPrice()
        binding.totalDetail.text = "Total : $total"
    }
}