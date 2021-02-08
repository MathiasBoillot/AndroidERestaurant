package fr.isen.boillot.androiderestaurant.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.boillot.androiderestaurant.BaseActivity.Companion.FILE_ORDER
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
        val itemQuantity = binding.itemQuantity
        val deleteItem = binding.itemDelete
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
        val file = File(context.cacheDir.absolutePath + "/$FILE_ORDER")

        file.exists().let {
            if (orders.order[position].quantity > 1) {
                orders.order[position].quantity--
            } else if (orders.order[position].quantity == 1) {
                orders.order.removeAt(position)
            }
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

}