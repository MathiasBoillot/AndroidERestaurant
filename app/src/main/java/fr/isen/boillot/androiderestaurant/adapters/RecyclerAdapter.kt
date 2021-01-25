package fr.isen.boillot.androiderestaurant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.boillot.androiderestaurant.databinding.ItemRowBinding

class RecyclerAdapter(private val categories: List<String>) :
    RecyclerView.Adapter<RecyclerAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.CategoryHolder, position: Int) {
        holder.title.text = categories[position]
    }

    override fun getItemCount(): Int = categories.size

    class CategoryHolder(binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.itemRow
    }
}