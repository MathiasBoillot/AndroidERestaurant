package fr.isen.boillot.androiderestaurant.category

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.boillot.androiderestaurant.BaseActivity
import fr.isen.boillot.androiderestaurant.adapters.RecyclerAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.boillot.androiderestaurant.detail.DetailActivity
import fr.isen.boillot.androiderestaurant.model.domain.DataResult
import fr.isen.boillot.androiderestaurant.model.domain.Item
import org.json.JSONException
import org.json.JSONObject


@Suppress("DEPRECATION")
class CategoryActivity : BaseActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra(CATEGORY).toString()
        title = category

        val messageTextView: TextView = binding.textView
        messageTextView.text = "Notre carte"

        swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            resetCache()
            getItems(category)
        }
        getItems(category)

    }

    private fun getItems(category: String?) {

        getCache()?.let {
            parseResult(it, category)
        } ?: run {


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
                { it ->
                    swipeRefreshLayout.isRefreshing = false
                    setCache(it.toString())
                    parseResult(it.toString(), category)
                }
            ) { error ->
                // Handle error
                error.printStackTrace()
            }
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
    }


    private fun setCache(res: String) {
        val sharedPreferences = getSharedPreferences(FILE_PREF, MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(CACHE_CATEGORY, res)
        }.apply()
    }

    private fun getCache(): String? {
        val sharedPreferences = getSharedPreferences(FILE_PREF, MODE_PRIVATE)
        return sharedPreferences.getString(CACHE_CATEGORY, null)
    }

    private fun resetCache() {
        val sharedPreferences = getSharedPreferences(FILE_PREF, MODE_PRIVATE)
        sharedPreferences.edit().apply {
            remove(CACHE_CATEGORY)
        }.apply()
    }

    private fun parseResult(res: String, selectedItem: String?) {
        val menuResult = GsonBuilder().create().fromJson(res, DataResult::class.java)
        val items = menuResult.data.firstOrNull { it.name == selectedItem }
        loadList(items?.items)
    }

    private fun loadList(items: List<Item>?) {
        binding.categoryLoader.isVisible = false
        swipeRefreshLayout.isRefreshing = false
        items?.let {
            val adapter = RecyclerAdapter(it) { item ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(ITEM, item)
                startActivity(intent)
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }


    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

}

