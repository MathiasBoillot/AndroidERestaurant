package fr.isen.boillot.androiderestaurant.model.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserResult(
    @SerializedName("data") val data : LoginResult
) : Serializable