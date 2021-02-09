package fr.isen.boillot.androiderestaurant.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.boillot.androiderestaurant.R
import fr.isen.boillot.androiderestaurant.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    arguments?.getString("URL").let {
            Picasso.get().load(it).placeholder(R.drawable.pick_go).into(binding.fragmentImage)
        }
    }

    companion object {
        fun newInstance(picture: String?): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply { putString("URL", picture) }
            }
        }
    }
}