package fr.isen.boillot.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.*
import com.google.gson.Gson
import fr.isen.boillot.androiderestaurant.databinding.ActivitySignInBinding
import fr.isen.boillot.androiderestaurant.login.LoginFormData
import fr.isen.boillot.androiderestaurant.login.LoginViewModel
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataForm: LoginFormData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = LoginViewModel()

        binding.login.setOnClickListener {

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

        binding.login.isEnabled = loginViewModel.loginDataChanged(dataForm)
    }


}