package fr.isen.boillot.androiderestaurant.model.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Price(
    @SerializedName("price") val price: String,
    @SerializedName("size") val size: String,
) : Serializable