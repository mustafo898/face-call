package dark.composer.fakecallapp.call

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.contacts.dialog.AgainDialog
import dark.composer.fakecallapp.databinding.FragmentThanksForCallBinding

class ThanksForCallFragment :
    BaseFragment<FragmentThanksForCallBinding>(FragmentThanksForCallBinding::inflate) {
    override fun onViewCreate() {
        binding.back.setOnClickListener {
            navController.navigate(R.id.action_thanksForCallFragment_to_homeFragment)
        }

        binding.settings.setOnClickListener {
            navController.navigate(R.id.action_thanksForCallFragment_to_settingsFragment)
        }

        binding.l1.setOnClickListener {
            navController.navigate(R.id.action_thanksForCallFragment_to_contactsFragment)
        }

        binding.game.setOnClickListener {
            navController.navigate(
                R.id.action_thanksForCallFragment_to_webViewFragment,
                bundleOf("url" to "https://www.gamezop.com/?id=3178")
            )
        }

        binding.share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
        binding.rate.setOnClickListener {
            var url =
                "https://play.google.com/store/apps/details?id=com.fakecallpoliceapplab"
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        binding.callAgain.setOnClickListener {
            val dialog = AgainDialog(requireContext()) {
                when (it) {
                    0 -> navController.navigate(R.id.action_thanksForCallFragment_to_chatFragment)
                    1 -> navController.navigate(R.id.action_thanksForCallFragment_to_liveFragment)
                    2 -> navController.navigate(R.id.action_thanksForCallFragment_to_callFragment)
                    3 -> navController.navigate(R.id.action_thanksForCallFragment_to_videoFragment)
                }

            }
            dialog.show()
        }
    }

    override fun onBackPressed() {
        navController.navigate(R.id.action_thanksForCallFragment_to_homeFragment)
    }
}