package fr.isen.boillot.androiderestaurant.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity, private val items: List<String>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = items.size


    override fun createFragment(position: Int): Fragment {
        return DetailFragment.newInstance(items[position])
    }
}