package fr.isen.boillot.androiderestaurant.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.boillot.androiderestaurant.R
import fr.isen.boillot.androiderestaurant.databinding.ItemRowBinding
import fr.isen.boillot.androiderestaurant.model.Item

class RecyclerAdapter(private val categories: List<Item>, private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.CategoryHolder, position: Int) {
        holder.title.text = categories[position].name
        holder.price.text = categories[position].getFormattedPrice()
        holder.ingredient.text = categories[position].getIngredients()
        if(categories[position].getFirstPicture().isNullOrEmpty()) {
            Picasso.get()
                .load("https://img.cuisineaz.com/660x660/2014-04-07/i58810-carpaccio-de-saumon.jpg")
                .placeholder(R.drawable.ic_baseline_image_search_24)
                .into(holder.images)
        } else {
            Picasso.get()
                .load(categories[position].getFirstPicture())
                .placeholder(R.drawable.ic_baseline_image_search_24)
                .into(holder.images)
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