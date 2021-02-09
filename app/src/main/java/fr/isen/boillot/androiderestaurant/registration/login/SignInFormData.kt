package fr.isen.boillot.androiderestaurant.registration.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignInFormData(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
) : Serializable
