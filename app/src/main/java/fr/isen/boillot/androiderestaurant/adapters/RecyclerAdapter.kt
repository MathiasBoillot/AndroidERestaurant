package fr.isen.boillot.androiderestaurant.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.boillot.androiderestaurant.HomeActivity
import fr.isen.boillot.androiderestaurant.ListActivity
import fr.isen.boillot.androiderestaurant.R
import fr.isen.boillot.androiderestaurant.databinding.ItemRowBinding
import fr.isen.boillot.androiderestaurant.model.Item
import fr.isen.boillot.androiderestaurant.model.Price

class RecyclerAdapter(private val categories: List<Item>, private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.CategoryHolder, position: Int) {
        holder.title.text = categories[position].name
        holder.price.text = categories[position].getFormattedPrice()
        holder.ingredient.text = categories[position].ingredients.map { it.name }.toString()
        if(categories[position].getFirstPicture().isNullOrEmpty()) {
            Picasso.get().load("https://t3.ftcdn.net/jpg/00/78/20/58/240_F_78205827_7oYojKCyxIhw0oitmk6gqoEo12mDkBdi.jpg").into(holder.images)
        } else {
            Picasso.get().load(categories[position].getFirstPicture()).into(holder.images)
        }

        val textView = holder.title
        textView.setOnClickListener {
//            val intent = Intent(context, ListActivity::class.java)
//            intent.putExtra(HomeActivity.CATEGORY, holder.title.text.toString())
//            context.startActivity(intent)
            Toast.makeText(context, textView.text, Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoryHolder(binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.itemTitle
        val images = binding.itemPic
        val price = binding.itemPrice
        val ingredient = binding.itemDescription
    }
}