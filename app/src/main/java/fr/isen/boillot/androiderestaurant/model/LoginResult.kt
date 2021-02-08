package fr.isen.boillot.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class LoginResult(
    @SerializedName("id") val id : Int
) : Serializable