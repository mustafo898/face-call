package dark.composer.fakecallapp.call

import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentCallBinding

class CallFragment : BaseFragment<FragmentCallBinding>(FragmentCallBinding::inflate) {
    override fun onViewCreate() {
        binding.accept.setOnClickListener {
            navController.navigate(R.id.action_callFragment_to_acceptCallFragment)
        }
        binding.decline.setOnClickListener {
            navController.navigate(R.id.action_callFragment_to_contactsFragment)
        }
    }

    override fun onBackPressed() {

    }
}