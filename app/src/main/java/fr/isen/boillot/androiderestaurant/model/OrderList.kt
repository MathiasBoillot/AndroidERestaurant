package fr.isen.boillot.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderList(
        @SerializedName("order") val order: MutableList<Order>
) : Serializable