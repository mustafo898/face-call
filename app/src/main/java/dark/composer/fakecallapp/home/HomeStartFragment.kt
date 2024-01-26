package dark.composer.fakecallapp.home

import android.content.Intent
import android.net.Uri
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentHomeStartBinding

class HomeStartFragment :
    BaseFragment<FragmentHomeStartBinding>(FragmentHomeStartBinding::inflate) {
    override fun onViewCreate() {
        binding.next.setOnClickListener {
//            showAd()
            navController.navigate(R.id.action_homeStartFragment_to_homeFragment)
        }

        binding.share.setOnClickListener {
            share()
        }
        binding.rate.setOnClickListener {
            rate()
        }
        binding.exit.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onBackPressed() {

    }
}