package dark.composer.fakecallapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!
    lateinit var navController: NavController
    private var isUseBackPress = true
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

    fun share() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share To:"))
    }

    fun rate() {
        var url = resources.getString(R.string.rate)
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun privacy() {
        val googleUri = Uri.parse(resources.getString(R.string.privacy_link))
        val googleIntent = Intent(Intent.ACTION_VIEW, googleUri)
        startActivity(googleIntent)
    }

    fun license() {
        val googleUri = Uri.parse(resources.getString(R.string.license))
        val googleIntent = Intent(Intent.ACTION_VIEW, googleUri)
        startActivity(googleIntent)
    }


    fun game() {
        var url = "https://www.gamezop.com/?id=3178"
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun moreGame() {
        var url =
            "https://play.google.com/store/apps/developer?id=Fake+Call+Chat+Pranks+by+AMYMOT+Dev"
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    abstract fun onViewCreate()
}
