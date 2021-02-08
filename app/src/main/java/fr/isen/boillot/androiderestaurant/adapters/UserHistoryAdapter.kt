package fr.isen.boillot.androiderestaurant.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.databinding.ItemUserAccountBinding
import fr.isen.boillot.androiderestaurant.model.Message
import fr.isen.boillot.androiderestaurant.model.Order
import fr.isen.boillot.androiderestaurant.model.OrderList
import fr.isen.boillot.androiderestaurant.model.PreviousOrderList

class UserHistoryAdapter(private val data: List<Message>, private val ct: Context) :
    RecyclerView.Adapter<UserHistoryAdapter.HistoryHolder>() {

    class HistoryHolder(binding: ItemUserAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryHolder {
        val itemBinding =
            ItemUserAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        var text = ""
        val orderCart = Gson().fromJson(data[position].message, OrderList::class.java) as OrderList
        for(c in orderCart.order){
            text += c.item.name + " x " + c.quantity
            Log.d("text", text)
        }
        holder.title.text = text
    }

    override fun getItemCount(): Int = data.size


}