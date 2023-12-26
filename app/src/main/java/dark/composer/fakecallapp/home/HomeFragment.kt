package dark.composer.fakecallapp.home

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreate() {
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
        binding.game.setOnClickListener {
            navController.navigate(
                R.id.action_homeFragment_to_webViewFragment,
                bundleOf("url" to "https://www.gamezop.com/?id=3178")
            )
        }
    }
}