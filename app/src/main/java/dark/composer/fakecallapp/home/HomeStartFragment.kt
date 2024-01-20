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
            showAd()
            navController.navigate(R.id.action_homeStartFragment_to_homeFragment)
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
        binding.exit.setOnClickListener {
            activity?.finish()
        }
    }
}