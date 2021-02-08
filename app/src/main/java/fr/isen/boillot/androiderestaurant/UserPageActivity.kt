package fr.isen.boillot.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.BaseActivity.Companion.ID
import fr.isen.boillot.androiderestaurant.adapters.UserHistoryAdapter
import fr.isen.boillot.androiderestaurant.databinding.ActivityUserPageBinding
import fr.isen.boillot.androiderestaurant.model.PreviousOrderList
import org.json.JSONException
import org.json.JSONObject

class UserPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserPageBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferences = getSharedPreferences(BaseActivity.FILE_PREF, Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString(ID, "0")

        if( userId != "0") {
            userId?.let { checkPreviousOrders(it.toInt()) }
            binding.logout.setOnClickListener {
                sharedPreferences.edit().remove(ID).apply()
                startActivity(Intent(this, HomeActivity::class.java))
            }
        } else {
            binding.logout.text = "Se connecter"
            binding.logout.setOnClickListener {
                startActivity(Intent(this, SignInActivity::class.java))
            }
        }
    }


    private fun checkPreviousOrders(user_id: Int) {
        val postUrl = "http://test.api.catering.bluecodegames.com/listorders"
        val queue = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("id_user", user_id)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val request = JsonObjectRequest(
            Request.Method.POST,
            postUrl,
            postData,
            { response ->
                Log.i("Test ", "" + response)
                val jsonResult: PreviousOrderList = Gson().fromJson(response.toString(),
                    PreviousOrderList::class.java
                )
                jsonResult.data.let {
                    binding.userRecyclerView.adapter = UserHistoryAdapter(it, this)
                    Log.d("Coucou", "coucou")
                }
                Log.i("JSON", jsonResult.toString())
            },
            { error ->
                error.printStackTrace()
            })
        queue.add(request)
    }
}