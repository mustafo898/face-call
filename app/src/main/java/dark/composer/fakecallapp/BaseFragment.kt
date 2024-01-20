package dark.composer.fakecallapp

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!
    lateinit var navController: NavController
    private var isUseBackPress = true

    private var rewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        onViewCreate()
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                isUseBackPress = true
                onBackPressed()
                return@setOnKeyListener isUseBackPress
            } else return@setOnKeyListener false
        }
    }

    open fun onBackPressed() {
        isUseBackPress = false
    }

    fun loadRewardedAd() {
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

    fun showAd() {
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

    abstract fun onViewCreate()
}
