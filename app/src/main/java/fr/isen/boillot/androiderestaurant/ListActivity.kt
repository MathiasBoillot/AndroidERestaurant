package fr.isen.boillot.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
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


@Suppress("DEPRECATION")
class ListActivity : BaseActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityListBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra(CATEGORY).toString()
        title = category
        val messageTextView: TextView = binding.textView
        messageTextView.text = "Notre carte"

        swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 4000)
        }
        postData(category)

    }

    private fun postData(category: String?) {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val params = JSONObject()
        try {
            params.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
//        // Instantiate the cache
//        val cache = DiskBasedCache(cacheDir, 4096 * 4096) // 2MB cap
//
//        // Set up the network to use HttpURLConnection as the HTTP client.
//        val network = BasicNetwork(HurlStack())
//
//        // Instantiate the RequestQueue with the cache and network. Start the queue.
//        val requestQueue = RequestQueue(cache, network).apply {
//            start()
//        }

        val stringRequest = JsonObjectRequest(Request.Method.POST, url, params,
            { it ->
                val gson: DataResult = Gson().fromJson(
                    it.toString(),
                    DataResult::class.java
                )
                gson.data.firstOrNull { it.name == category }?.items?.let { items ->
                    displayCategories(items)
                } ?: run {
                    Log.e("ListActivity", "Pas de catégorie trouvée")
                }
            }
        ) { error ->
            // Handle error
            error.printStackTrace()
        }

        // Reset the cache
        //requestQueue.cache.clear()
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }


    private fun displayCategories(category: List<Item>) {
        binding.categoryLoader.isVisible = false
        binding.recyclerView.isVisible = true
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = RecyclerAdapter(category) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ITEM, it)
            intent.putExtra(CATEGORY, category.toString())
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

}

