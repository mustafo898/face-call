package dark.composer.fakecallapp.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private var rewardedAd: RewardedAd? = null

    override fun onViewCreate() {

        loadRewardedAd()

        binding.call.setOnClickListener {
            showAd()
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
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
        binding.rate.setOnClickListener {
            var url = "https://play.google.com/store/apps/details?id=com.fakecallpoliceapplab"
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        binding.game.setOnClickListener {
            navController.navigate(
                R.id.action_homeFragment_to_webViewFragment,
                bundleOf("url" to "https://www.gamezop.com/?id=3178")
            )
        }
    }


    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()

        Log.e("FABKFA", "loadRewardedAd: ${rewardedAd?.adUnitId}")
        RewardedAd.load(requireActivity(),
            "ca-app-pub-7173802867165820/3073425903",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    Toast.makeText(requireContext(), "ad's loaded", Toast.LENGTH_SHORT).show()
                    Log.e("FABKFA", "onAdFailedToLoad: {ad's loaded}")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Toast.makeText(requireContext(), "failed to load", Toast.LENGTH_SHORT).show()
                    Log.e("FABKFA", "onAdFailedToLoad: ${loadAdError.cause}")
                    Log.e("FABKFA", "onAdFailedToLoad: ${loadAdError.message}")
                }
            })
    }

    private fun showAd() {
        rewardedAd?.let {
            it.show(requireActivity()) { rewardItem ->
                // Handle the reward
                Toast.makeText(requireContext(), "ad's loaded", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            // Ad not loaded, handle accordingly
            loadRewardedAd()
            Toast.makeText(requireContext(), "ad not loaded", Toast.LENGTH_SHORT).show()
        }
    }
}