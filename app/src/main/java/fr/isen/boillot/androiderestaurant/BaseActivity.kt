package fr.isen.boillot.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.boillot.androiderestaurant.cart.CartActivity
import fr.isen.boillot.androiderestaurant.userPage.UserPageActivity

open class BaseActivity : AppCompatActivity() {
    // Manage cart toolbar layout


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.basket_menu, menu)
        val cartView = (menu?.findItem(R.id.showBasket)?.actionView).apply {
            setupBadge(this)
        }

        val userView = (menu?.findItem(R.id.showUserAccount)?.actionView)


        cartView?.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        userView?.setOnClickListener {
            startActivity(Intent(this, UserPageActivity::class.java))
        }

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(FILE_PREF, Context.MODE_PRIVATE)
        if(sharedPreferences.getInt(ID, 0) != 0)
            userView?.findViewById<ImageView>(R.id.userStatus)?.setImageResource(R.drawable.badge_status_connected)
        else
            userView?.findViewById<ImageView>(R.id.userStatus)?.setImageResource(R.drawable.badge_status_disconnected)
        invalidateOptionsMenu()



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

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    companion object {
        const val FILE_ORDER = "cart.json"
        const val FILE_PREF = "app_pref"
        const val BASKET_COUNTER = "counter"
        const val ITEM = "item"
        const val CATEGORY = "category_key"
        const val ID = "user_id"
        const val CACHE_CATEGORY = "cache_category"
    }

}