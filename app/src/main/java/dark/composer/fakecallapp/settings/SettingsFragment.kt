package dark.composer.fakecallapp.settings

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val adapter by lazy {
        SettingsAdapter {
            when (it) {
                0 -> navController.navigate(
                    R.id.action_settingsFragment_to_webViewFragment,
                    bundleOf("url" to "https://creativecommons.org/publicdomain/zero/1.0/  ")
                )

                1 -> navController.navigate(
                    R.id.action_settingsFragment_to_webViewFragment,
                    bundleOf("url" to "https://sites.google.com/view/dog-fake-video-call/ ")
                )

                2 -> {
                    var url =
                        "https://play.google.com/store/apps/details?id=com.dolzhenko.dogfakecallapp \n"
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://$url"
                    }

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                }

                3 -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
                    intent.type = "text/plain"
                    startActivity(Intent.createChooser(intent, "Share To:"))
                }

                4 -> {
                    var url =
                        "https://play.google.com/store/apps/developer?id=Fake+Call+Chat+Pranks+by+AMYMOT+Dev \n"
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://$url"
                    }

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                }

                5 -> navController.navigate(
                    R.id.action_settingsFragment_to_webViewFragment,
                    bundleOf("url" to "https://www.gamezop.com/?id=3178")
                )
            }
        }
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }

    override fun onViewCreate() {
        val list = mutableListOf<SettingsModel>()
        binding.rv.adapter = adapter
        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        list.add(SettingsModel("License", R.drawable.license))
        list.add(SettingsModel("Privacy Policy", R.drawable.privacy))
        list.add(SettingsModel("Rate us", R.drawable.like))
        list.add(SettingsModel("Share it", R.drawable.share))
        list.add(SettingsModel("More Games", R.drawable.more))
        list.add(SettingsModel("Play Games", R.drawable.game))

        adapter.set(list)

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }
}