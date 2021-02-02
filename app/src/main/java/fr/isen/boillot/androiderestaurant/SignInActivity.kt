package fr.isen.boillot.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.boillot.androiderestaurant.databinding.ActivitySignInBinding
import fr.isen.boillot.androiderestaurant.login.SignInFormData
import fr.isen.boillot.androiderestaurant.login.LoginViewModel
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
        }) {
                error -> error.printStackTrace()
        }
        queue.add(request)
    }

    companion object {
        const val ID = "user_id"
    }
}