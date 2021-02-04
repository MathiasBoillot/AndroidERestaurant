package fr.isen.boillot.androiderestaurant

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.boillot.androiderestaurant.databinding.ActivityUserAccountBinding

class UserAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView12.setOnClickListener {
            val sharedPreferences: SharedPreferences =
                getSharedPreferences(BaseActivity.FILE_PREF, Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString(BaseActivity.ID, "0")
            }.apply()
            invalidateOptionsMenu()
        }
    }
}