package fr.isen.boillot.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderList(
        @SerializedName("order") val order: MutableList<Order>
) : Serializable {
    fun totalPriceOrder(): String {
        var totalPrice = 0.00
        for (eachOrder in this.order)
            totalPrice += eachOrder.quantity * eachOrder.item.getPrice()
        return totalPrice.toString() + '€'
    }
}