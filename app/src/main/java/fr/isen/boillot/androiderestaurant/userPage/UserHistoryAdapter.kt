package fr.isen.boillot.androiderestaurant.userPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.databinding.ItemUserPageBinding
import fr.isen.boillot.androiderestaurant.model.domain.Message
import fr.isen.boillot.androiderestaurant.model.domain.OrderList

class UserHistoryAdapter(private val data: List<Message>) :
    RecyclerView.Adapter<UserHistoryAdapter.HistoryHolder>() {

    class HistoryHolder(binding: ItemUserPageBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryHolder {
        val itemBinding =
            ItemUserPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        var text = ""
        val orderCart = Gson().fromJson(data[position].message, OrderList::class.java) as OrderList

        // For all the items in the order.. Add it to "text"
        for (c in orderCart.order) {
            text += c.item.name + " x " + c.quantity + "\n"
        }
        holder.title.text = text
    }

    override fun getItemCount(): Int = data.size


}