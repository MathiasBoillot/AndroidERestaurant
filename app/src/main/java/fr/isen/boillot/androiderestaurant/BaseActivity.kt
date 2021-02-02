package fr.isen.boillot.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

open class BaseActivity : AppCompatActivity() {
    // Manage cart toolbar layout


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.basket_menu, menu)
        val menuView = (menu?.findItem(R.id.show_basket)?.actionView).apply {
            setupBadge(this)
        }


        menuView?.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }


        return super.onCreateOptionsMenu(menu)
    }

    private fun setupBadge(item: View?) {
        val counter = item?.findViewById<TextView>(R.id.nbItems)
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(FILE_PREF, Context.MODE_PRIVATE)
        val quantity = sharedPreferences.getInt(BASKET_COUNTER, 0)
        if (quantity == 0) {
            counter?.isVisible = false
        } else {
            counter?.text = quantity.toString()
            counter?.isVisible = true
        }
    }

    companion object {
        const val FILE_ORDER = "cart.json"
        const val FILE_PREF = "app_pref"
        const val BASKET_COUNTER = "counter"
        const val ITEM = "item"
        const val CATEGORY = "category_key"
        const val ID = "user_id"

    }

}