package fr.isen.boillot.androiderestaurant

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.model.DataResult
import fr.isen.boillot.androiderestaurant.adapters.RecyclerAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityListBinding
import fr.isen.boillot.androiderestaurant.model.Item
import org.json.JSONException
import org.json.JSONObject


class ListActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("category_key")
        title = category
        val messageTextView: TextView = binding.textView
        messageTextView.text = "Notre carte"

        postData(category)



    }

    private fun postData(category : String?) {
        val textView = findViewById<TextView>(R.id.text)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val params = JSONObject()
        try {
            params.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val stringRequest = JsonObjectRequest(Request.Method.POST, url, params,
            {
                Log.d("json", it.toString())
                val gson: DataResult = Gson().fromJson(it.toString(), DataResult::class.java)
               gson.data.firstOrNull{ it.name == category}?.items?.let{ items ->
                   displayCategories(items)
               } ?: run {
                   Log.e("ListActivity", "Pas de catégorie trouvée")
               }
                Log.d("data", gson.toString())
            },
            { textView.text = "That didn't work!" })



        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun displayCategories(category: List<Item>) {
        binding.categoryLoader.isVisible = false
        binding.recyclerView.isVisible = true
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = RecyclerAdapter(category, applicationContext)
//        var listPlats: List<String>? = null
//        if (category.equals("Entrées")) {
//            listPlats = resources.getStringArray(R.array.starter_title).toList()
//        }
//        if (category.equals("Plats")) {
//            listPlats = resources.getStringArray(R.array.main_name).toList()
//        }
//        if (category.equals("Desserts")) {
//            listPlats = resources.getStringArray(R.array.dessert_name).toList()
//        }
//        binding.recyclerView.adapter = listPlats?.let { RecyclerAdapter(it, applicationContext) }
//        val dividerItemDecoration = DividerItemDecoration(
//            binding.recyclerView.getContext(),
//            linearLayoutManager.getOrientation()
//        )
//        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }
}

