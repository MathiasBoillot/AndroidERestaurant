package fr.isen.boillot.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreviousOrderList(
    @SerializedName("data") val data: List<Message>
) : Serializable {
}

data class Message(
    @SerializedName("message") val message: String,
    @SerializedName("create-date") val create_date: Any
)