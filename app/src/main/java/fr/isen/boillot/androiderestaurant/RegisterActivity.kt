package fr.isen.boillot.androiderestaurant

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.boillot.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.boillot.androiderestaurant.login.LoginFormData
import fr.isen.boillot.androiderestaurant.login.LoginViewModel
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataForm: LoginFormData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = LoginViewModel()

        binding.signInBtn.setOnClickListener {
            createAccount()
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        loginViewModel = LoginViewModel()
        dataForm = LoginFormData(
            email = binding.registerEmail.text.toString(),
            firstName = binding.registerFirstname.text.toString(),
            lastName = binding.registerLastname.text.toString(),
            address = binding.registerAddress.text.toString(),
            password = binding.registerPassword.text.toString()
        )

        binding.signInBtn.isEnabled = loginViewModel.loginDataChanged(dataForm)
    }

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
        }) {
                error -> error.printStackTrace()
        }
        queue.add(request)
    }
}