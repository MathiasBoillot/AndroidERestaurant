package fr.isen.boillot.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.boillot.androiderestaurant.adapters.RecyclerAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityListBinding


class ListActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("category_key")
        title = message
        val messageTextView: TextView = binding.textView
        messageTextView.text = message

        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        var listPlats: List<String>? = null
        if (message.equals("Entrées")) {
            listPlats = resources.getStringArray(R.array.starter_name).toList()
        }
        if (message.equals("Plats")) {
            listPlats = resources.getStringArray(R.array.main_name).toList()
        }
        if (message.equals("Desserts")) {
            listPlats = resources.getStringArray(R.array.dessert_name).toList()
        }
        binding.recyclerView.adapter = listPlats?.let { RecyclerAdapter(it) }
        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.getContext(),
            linearLayoutManager.getOrientation()
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    fun displayDetailMeal() {
        TODO()
    }
}

