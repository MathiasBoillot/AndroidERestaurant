package fr.isen.boillot.androiderestaurant.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.isen.boillot.androiderestaurant.HomeActivity
import fr.isen.boillot.androiderestaurant.ListActivity
import fr.isen.boillot.androiderestaurant.databinding.ItemRowBinding

class RecyclerAdapter(private val categories: List<String>, private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.CategoryHolder, position: Int) {
        holder.title.text = categories[position]
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
    }
}