package dark.composer.fakecallapp.settings

import androidx.core.os.bundleOf
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentExitBinding

class ExitFragment : BaseFragment<FragmentExitBinding>(FragmentExitBinding::inflate) {
    override fun onViewCreate() {
        binding.back.setOnClickListener {
            showAd()
            navController.popBackStack()
        }
        binding.game.setOnClickListener {
            showAd()
            navController.navigate(
                R.id.action_exitFragment_to_webViewFragment,
                bundleOf("url" to "https://www.gamezop.com/?id=3178")
            )
        }
        binding.play.setOnClickListener {
            showAd()
            navController.navigate(
                R.id.action_exitFragment_to_webViewFragment,
                bundleOf("url" to "https://www.gamezop.com/?id=3178")
            )
        }
        binding.backToMenu.setOnClickListener {
            showAd()
            navController.navigate(R.id.action_exitFragment_to_homeFragment)
        }
    }
}