package dark.composer.fakecallapp.home

import android.util.Log
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentHomeBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref
import kotlin.math.log

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    override fun onViewCreate() {
        val sharedPref = EncryptedSharedPref(requireContext())

        sharedPref.getList().forEach {
            if (it.selected){
                binding.image.setImageResource(it.image)
                binding.name.setText(it.name)
                Log.d("otpiytlhngngd", "onViewCreate:")
            }
        }

//        loadRewardedAd()

//        showAd()

        binding.back.setOnClickListener {
            navController.navigate(R.id.homeStartFragment)
        }
        binding.settings.setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }

        binding.call.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_callFragment)
        }
        binding.videoCall.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_videoFragment)
        }
        binding.live.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_liveFragment)
        }
        binding.chat.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_chatFragment)
        }
        binding.characters.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_contactsFragment)
        }

        binding.share.setOnClickListener {
            share()
        }
        binding.rate.setOnClickListener {
            rate()
        }
        binding.game.setOnClickListener {
            game()
        }
    }

    override fun onBackPressed() {

    }
}