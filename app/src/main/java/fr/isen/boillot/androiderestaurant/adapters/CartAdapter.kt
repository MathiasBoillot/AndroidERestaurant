package fr.isen.boillot.androiderestaurant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.boillot.androiderestaurant.R
import fr.isen.boillot.androiderestaurant.databinding.ItemOrderCartBinding
import fr.isen.boillot.androiderestaurant.model.OrderList

class CartAdapter(private var orders: OrderList) : RecyclerView.Adapter<CartAdapter.CartHolder>() {
    class CartHolder(binding: ItemOrderCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName = binding.itemName
        val itemPrice = binding.itemPriceTotal
        val images = binding.itemPic
        val layout = binding.root
        val itemQuantity = binding.itemQuantity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val itemBinding = ItemOrderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.itemName.text = orders.order[position].item.name
        holder.itemPrice.text = "Total : " + orders.order[position].totalPriceFormatted()
        if(orders.order[position].item.getFirstPicture().isNullOrEmpty()) {
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
        ("Quantité : " + orders.order[position].quantity.toString()).also { holder.itemQuantity.text = it }
    }

    override fun getItemCount(): Int = orders.order.size

}