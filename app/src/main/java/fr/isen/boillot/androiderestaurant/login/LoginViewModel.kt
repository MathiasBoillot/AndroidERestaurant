package fr.isen.boillot.androiderestaurant.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.isen.boillot.androiderestaurant.R


class LoginViewModel() : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm


    fun registerDataChanged(dataForm: RegisterFormData): Boolean {
        return if (!isEmailValid(dataForm.email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
            Log.i("Value email", dataForm.email)
            false
        } else if (!isNameValid(dataForm.firstName)) {
            _loginForm.value = LoginFormState(firstNameError = R.string.invalid_firstName)
            Log.i("Value firstName", dataForm.firstName)
            false
        } else if (!isNameValid(dataForm.lastName)) {
            _loginForm.value = LoginFormState(lastNameError = R.string.invalid_lastName)
            Log.i("Value lastName", dataForm.lastName)
            false
        } else if (!isNameValid(dataForm.address)) {
            _loginForm.value = LoginFormState(addressError = R.string.invalid_address)
            Log.i("Value address", dataForm.address)
            false
        } else if (!isPasswordValid(dataForm.password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            Log.i("Value password", dataForm.password)
            false
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
            true
        }
    }

    fun signInDataChanged(dataForm: SignInFormData): Boolean {
        return if (!isEmailValid(dataForm.email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
            false
        } else if (!isPasswordValid(dataForm.password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            false
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
            true
        }
    }

    // A placeholder email validation check
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder firstName / lastname / address validation check
    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank()
    }


    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        val PASSWORD_PATTERN =  Regex("^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{12,}" +               //at least 4 characters
                "$")
        return PASSWORD_PATTERN.matches(password).toString() == "true"
    }


}