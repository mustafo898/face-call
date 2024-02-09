package dark.composer.fakecallapp.call

import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.call.adapter.MoreContactsAdapter
import dark.composer.fakecallapp.contacts.dialog.AgainDialog
import dark.composer.fakecallapp.databinding.FragmentThanksForCallBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref

class ThanksForCallFragment :
    BaseFragment<FragmentThanksForCallBinding>(FragmentThanksForCallBinding::inflate) {

    private val contactAdapter by lazy {
        MoreContactsAdapter(requireContext())
    }

    override fun onViewCreate() {
        val sharedPref = EncryptedSharedPref(requireContext())

        sharedPref.getList().forEach {
            if (it.selected) {
                binding.image.setImageResource(it.image)
                binding.name.setText(it.name)

            }
        }

        binding.rv.adapter = contactAdapter
        contactAdapter.set(sharedPref.getList())
        binding.back.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        binding.settings.setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }

        binding.l1.setOnClickListener {
            navController.navigate(R.id.contactsFragment)
        }

        binding.view.setOnClickListener {
            navController.navigate(R.id.contactsFragment)
        }

        binding.game.setOnClickListener {
            game()
        }

        binding.share.setOnClickListener {
            share()
        }
        binding.rate.setOnClickListener {
            rate()
        }
        binding.callAgain.setOnClickListener {
            val dialog = AgainDialog(requireContext()) {
                when (it) {
                    0 -> navController.navigate(R.id.chatFragment)
                    1 -> navController.navigate(R.id.liveFragment)
                    2 -> navController.navigate(R.id.callFragment)
                    3 -> navController.navigate(R.id.videoFragment)
                }

            }
            dialog.show()
        }

    }

    override fun onBackPressed() {

    }
}