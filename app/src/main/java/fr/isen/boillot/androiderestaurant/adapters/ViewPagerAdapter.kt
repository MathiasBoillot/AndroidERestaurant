package fr.isen.boillot.androiderestaurant.adapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.boillot.androiderestaurant.fragment.DetailFragment

class ViewPagerAdapter(activity: AppCompatActivity, private val items: List<String>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = items.size


    override fun createFragment(position: Int): Fragment {
        return DetailFragment.newInstance(items[position])
    }
}