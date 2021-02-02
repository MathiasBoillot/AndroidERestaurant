package fr.isen.boillot.androiderestaurant.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    @SerializedName("emailError") val emailError: Int? = null,
    @SerializedName("firstNameError") val firstNameError: Int? = null,
    @SerializedName("lastNameError") val lastNameError: Int? = null,
    @SerializedName("addressError") val addressError: Int? = null,
    @SerializedName("passwordError") val passwordError: Int? = null,
    @SerializedName("isDataValid") val isDataValid: Boolean = false
) : Serializable