package fr.isen.boillot.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.databinding.ActivitySignInBinding
import fr.isen.boillot.androiderestaurant.login.SignInFormData
import fr.isen.boillot.androiderestaurant.login.LoginViewModel
import fr.isen.boillot.androiderestaurant.model.UserResult
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataForm: SignInFormData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = LoginViewModel()

        binding.loginBtn.setOnClickListener {
            signInAccount()
        }

        binding.registerSwitch.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        loginViewModel = LoginViewModel()
        dataForm = SignInFormData(
            email = binding.loginEmail.text.toString(),
            password = binding.loginPassword.text.toString()
        )

        binding.loginBtn.isEnabled = loginViewModel.signInDataChanged(dataForm)
    }

    private fun signInAccount() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/login"
        val dataPost = JSONObject().let {
            it.put("id_shop", "1")
            it.put("email", dataForm.email)
            it.put("password", dataForm.password)
        }

        val request = JsonObjectRequest(Request.Method.POST, url, dataPost, {
            Log.d("response", it.toString())
            val jsonResult : UserResult = Gson().fromJson(
                it.toString(),
                UserResult::class.java
            )
            val sharedPreference = getSharedPreferences(BaseActivity.FILE_PREF, MODE_PRIVATE)
            sharedPreference.edit().apply {
                putString(BaseActivity.ID, jsonResult.data.id)
            }.apply()
        }) {
                error -> error.printStackTrace()
        }
        queue.add(request)
    }
}