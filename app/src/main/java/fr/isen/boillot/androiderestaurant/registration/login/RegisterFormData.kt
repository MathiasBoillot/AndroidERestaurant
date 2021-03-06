package fr.isen.boillot.androiderestaurant.registration.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterFormData(
    @SerializedName("email") val email: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("password") val password: String,
    @SerializedName("address") val address: String,
) : Serializable
