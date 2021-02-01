package fr.isen.boillot.androiderestaurant.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import fr.isen.boillot.androiderestaurant.DetailActivity
import fr.isen.boillot.androiderestaurant.DetailActivity.Companion.FILE_PREF
import fr.isen.boillot.androiderestaurant.R
import fr.isen.boillot.androiderestaurant.databinding.ItemOrderCartBinding
import fr.isen.boillot.androiderestaurant.model.Order
import fr.isen.boillot.androiderestaurant.model.OrderList
import java.io.File

class CartAdapter(
    private val orders: OrderList,
    private val context: Context,
    private val deleteItemClickListener: (Order) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartHolder>() {
    class CartHolder(binding: ItemOrderCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName = binding.itemName
        val itemPrice = binding.itemPriceTotal
        val images = binding.itemPic
        val layout = binding.root
        val itemQuantity = binding.itemQuantity
        val deleteItem = binding.deleteItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val itemBinding =
            ItemOrderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.itemName.text = orders.order[position].item.name
        ("Total : " + orders.order[position].totalPriceFormatted()).also {
            holder.itemPrice.text = it
        }
        if (orders.order[position].item.getFirstPicture().isNullOrEmpty()) {
            Picasso.get()
                .load("https://img.cuisineaz.com/660x660/2014-04-07/i58810-carpaccio-de-saumon.jpg")
                .placeholder(R.drawable.ic_baseline_image_search_24)
                .into(holder.images)
        } else {
            Picasso.get()
                .load(orders.order[position].item.getFirstPicture())
                .placeholder(R.drawable.ic_baseline_image_search_24)
                .into(holder.images)
        }
        ("Quantité : " + orders.order[position].quantity.toString()).also {
            holder.itemQuantity.text = it
        }

        holder.deleteItem.setOnClickListener {
            deleteItemClickListener.invoke(orders.order[position])
            deleteItem(position)
        }
    }

    override fun getItemCount(): Int = orders.order.size

    private fun deleteItem(position: Int) {
        val file = File(context.cacheDir.absolutePath + "/${DetailActivity.FILE_ORDER}")

        file.exists().let {
            val orderList = Gson().fromJson(file.readText(), OrderList::class.java) as OrderList

            if (orderList.order[position].quantity > 1) {
                orderList.order[position].quantity--
                orders.order[position].quantity--
            } else if (orderList.order[position].quantity == 1) {
                orderList.order.removeAt(position)
                orders.order.removeAt(position)
            }
            cartItem(orderList)
            file.writeText(Gson().toJson(orderList))

            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }


    private fun cartItem(orders: OrderList) {
        val count = orders.order.sumOf { it.quantity }
        val sharedPreference = context.getSharedPreferences(FILE_PREF, AppCompatActivity.MODE_PRIVATE)
        sharedPreference.edit().apply {
            putInt("quantity", count )
        }.apply()
    }



}