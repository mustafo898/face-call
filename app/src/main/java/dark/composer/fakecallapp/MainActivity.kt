package dark.composer.fakecallapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dark.composer.fakecallapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val neededPermissions = arrayOf(Manifest.permission.CAMERA)
    private var TAG = "Main_Activity_"
    private var interstitialAd: InterstitialAd? = null
    private var isLoading = false
    private var isError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)

        hideNavigationBar()

        MobileAds.initialize(
            this
        ) { }

        loadInterAd()

        loadBanner()

        checkPermission()

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        navController = navHostFragment.findNavController()

        navController.enableOnBackPressed(true)

        navController.addOnDestinationChangedListener { _, destination, argument ->
            if (destination.id != R.id.splashFragment && destination.id != R.id.homeStartFragment && destination.id != R.id.acceptCallFragment && destination.id != R.id.videoAcceptFragment && destination.id != R.id.liveFragment) show()

            if (destination.id != R.id.chatFragment){
                loadBanner()
                binding.adView.visible()
            }else{
                binding.adView.destroy()
                binding.adView.gone()
            }
        }
    }

    private fun loadBanner(){
        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun hideNavigationBar() {
        val decorView = window.decorView
        var uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
    }

    private fun show() {
        if (interstitialAd != null) {
            interstitialAd!!.show(this)
            interstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    Log.d("skhdgfksjdwewiur", "onAdClicked: click")
                    super.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    Log.d(
                        "skhdgfksjdwewiur",
                        "onAdDismissedFullScreenContent: onAdDismissedFullScreenContent"
                    )
                    interstitialAd = null
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    Log.d(
                        "skhdgfksjdwewiur",
                        "onAdFailedToShowFullScreenContent: ${p0.message}\n${p0.cause}\n${p0.code}\n"
                    )
                    interstitialAd = null
                }

                override fun onAdImpression() {
                    Log.d("skhdgfksjdwewiur", "onAdImpression: onAdImpression")
                    super.onAdImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(
                        "skhdgfksjdwewiur",
                        "onAdShowedFullScreenContent: onAdShowedFullScreenContent"
                    )
                    super.onAdShowedFullScreenContent()
                }
            }
            interstitialAd = null
        } else {
            loadInterAd()
        }
    }

    private fun loadInterAd() {
        val adRequest = AdRequest.Builder().build()

        isLoading = true

        InterstitialAd.load(this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    interstitialAd = p0
                    isLoading = false
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    isError = true
                    Log.d("rrttyyyuoopw", "onAdFailedToLoad: ${p0.message}\n${p0.cause}")
                }
            })

    }


    private fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            val permissionsNotGranted = ArrayList<String>()
            for (permission in neededPermissions) {
                if (ContextCompat.checkSelfPermission(
                        this, permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsNotGranted.add(permission)
                }
            }
            if (permissionsNotGranted.size > 0) {
                var shouldShowAlert = false
                for (permission in permissionsNotGranted) {
                    shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(
                        this, permission
                    )
                }
                val arr = arrayOfNulls<String>(permissionsNotGranted.size)
                val permissions = permissionsNotGranted.toArray(arr)
                if (shouldShowAlert) {
                    requestPermissions(permissions)
                } else {
                    requestPermissions(permissions)
                }
                return false
            }
        }
        return true
    }

    private fun requestPermissions(permissions: Array<String?>) {
        ActivityCompat.requestPermissions(this, permissions, 21)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            21 -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        //t
                        return
                    }
                }
                // access
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.visibleOrGone(p: Boolean) {
    if (p) this.visible() else this.gone()
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}