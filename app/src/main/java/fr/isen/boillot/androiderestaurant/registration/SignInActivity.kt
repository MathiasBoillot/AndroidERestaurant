package fr.isen.boillot.androiderestaurant.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.BaseActivity
import fr.isen.boillot.androiderestaurant.cart.CartActivity
import fr.isen.boillot.androiderestaurant.databinding.ActivitySignInBinding
import fr.isen.boillot.androiderestaurant.registration.login.SignInFormData
import fr.isen.boillot.androiderestaurant.registration.login.LoginViewModel
import fr.isen.boillot.androiderestaurant.model.domain.UserResult
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

        // Send post to sign in the app
        binding.loginBtn.setOnClickListener {
            signInAccount()
        }

        // Switch to register activity if no account
        binding.registerSwitch.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    /**
     * After each action of the user
     * Check if the input is valid
     * if is valid -> Enable the button to be connected
     */
    override fun onUserInteraction() {
        super.onUserInteraction()
        loginViewModel = LoginViewModel()
        dataForm = SignInFormData(
            email = binding.loginEmail.text.toString(),
            password = binding.loginPassword.text.toString()
        )
        loginViewModel.loginFormState.observe(this@SignInActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.loginBtn.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                binding.loginEmail.error = getString(loginState.emailError)
            }
        })
        loginViewModel.signInDataChanged(dataForm)
    }

    /**
     * Send post request to sign in
     */
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
            val jsonResult: UserResult = Gson().fromJson(
                it.toString(),
                UserResult::class.java
            )
            val sharedPreference = getSharedPreferences(BaseActivity.FILE_PREF, MODE_PRIVATE)
            sharedPreference.edit().apply {
                putInt(BaseActivity.ID, jsonResult.data.id)
            }.apply()
            startActivity(Intent(this, CartActivity::class.java))
        }) { error ->
            error.printStackTrace()
        }
        queue.add(request)
    }
}