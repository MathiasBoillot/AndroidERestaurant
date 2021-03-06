package fr.isen.boillot.androiderestaurant.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.BaseActivity
import fr.isen.boillot.androiderestaurant.cart.CartActivity
import fr.isen.boillot.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.boillot.androiderestaurant.registration.login.RegisterFormData
import fr.isen.boillot.androiderestaurant.registration.login.LoginViewModel
import fr.isen.boillot.androiderestaurant.model.domain.UserResult
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataForm: RegisterFormData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = LoginViewModel()

        binding.signInBtn.setOnClickListener {
            createAccount()
        }

        // Switch to SignIn Activity if already account
        binding.signInSwitch.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }


    }

    /**
     * After each action of the user
     * Check if the input is valid
     * if is valid -> Enable the button to be registered
     */
    override fun onUserInteraction() {
        super.onUserInteraction()
        loginViewModel = LoginViewModel()
        dataForm = RegisterFormData(
            email = binding.registerEmail.text.toString(),
            firstName = binding.registerFirstname.text.toString(),
            lastName = binding.registerLastname.text.toString(),
            address = binding.registerAddress.text.toString(),
            password = binding.registerPassword.text.toString()
        )
        loginViewModel.loginFormState.observe(this@RegisterActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.signInBtn.isEnabled = loginState.isDataValid

            if (loginState.firstNameError != null) {
                binding.registerFirstname.error = getString(loginState.firstNameError)
            }
            if (loginState.lastNameError != null) {
                binding.registerLastname.error = getString(loginState.lastNameError)
            }
            if (loginState.emailError != null) {
                binding.registerEmail.error = getString(loginState.emailError)
            }
            if (loginState.addressError != null) {
                binding.registerAddress.error = getString(loginState.addressError)
            }
            if (loginState.passwordError != null) {
                binding.registerPassword.error = getString(loginState.passwordError)
            }
        })
        loginViewModel.registerDataChanged(dataForm)
    }

    /**
     * Send post request to be registered
     */
    private fun createAccount() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/register"
        val dataPost = JSONObject().let {
            it.put("id_shop", "1")
            it.put("email", dataForm.email)
            it.put("firstname", dataForm.firstName)
            it.put("lastname", dataForm.lastName)
            it.put("address", dataForm.address)
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
            binding.registerError.isVisible = true
        }
        queue.add(request)
    }
}