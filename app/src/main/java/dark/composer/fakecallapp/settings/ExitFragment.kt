package dark.composer.fakecallapp.settings

import androidx.core.os.bundleOf
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentExitBinding

class ExitFragment : BaseFragment<FragmentExitBinding>(FragmentExitBinding::inflate) {
    override fun onViewCreate() {
        binding.back.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }
        binding.game.setOnClickListener {
            game()
        }
        binding.play.setOnClickListener {
            game()
        }
        binding.backToMenu.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }
    }

    override fun onBackPressed() {

    }
}