package fr.isen.boillot.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
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
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("category_key")
        title = category
        val messageTextView: TextView = binding.textView
        messageTextView.text = "Notre carte"

        swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 4000)
        }
        postData(category)


    }

    private fun postData(category: String?) {
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
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 4096 * 4096) // 2MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val stringRequest = JsonObjectRequest(Request.Method.POST, url, params,
            {
                val gson: DataResult = Gson().fromJson(it.toString(), DataResult::class.java)
                gson.data.firstOrNull { it.name == category }?.items?.let { items ->
                    displayCategories(items)
                } ?: run {
                    Log.e("ListActivity", "Pas de catégorie trouvée")
                }
                Log.d("data", gson.toString())
            },
            { error ->
                // Handle error
                textView.text = "ERROR: %s".format(error.toString())
            })

        // Reset the cache
        //requestQueue.cache.clear()
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest)
    }


    private fun displayCategories(category: List<Item>) {
        binding.categoryLoader.isVisible = false
        binding.recyclerView.isVisible = true
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = RecyclerAdapter(category) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("item", it)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.basket_menu,menu)
        menu?.findItem(R.id.show_basket)?.let { setupBadge(it) }
        return super.onCreateOptionsMenu(menu)
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.show_basket -> {
            // User chose the "Print" item
            Toast.makeText(this,"Print action",Toast.LENGTH_LONG).show()
            true
        }
        android.R.id.home ->{
            Toast.makeText(this,"Home action",Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupBadge(item: MenuItem) {
        val textView = item.actionView.findViewById<TextView>(R.id.nbItems)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(FILE, Context.MODE_PRIVATE)

        if (sharedPreferences.contains("quantity")){
            val quantity = sharedPreferences.getInt("quantity", 0)
            if(quantity == 0){
                textView.isVisible = false

            } else {
                textView.text = quantity.toString()
                textView.isVisible = true
            }
        } else {
            textView.isVisible = false
        }
    }
    companion object {
        const val FILE = "cart.json"
    }
}

