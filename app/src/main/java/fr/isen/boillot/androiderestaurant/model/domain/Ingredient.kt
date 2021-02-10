package fr.isen.boillot.androiderestaurant.model.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredient(
    @SerializedName("name_fr") val name: String,
) : Serializable