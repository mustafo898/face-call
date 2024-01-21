package dark.composer.fakecallapp.home

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.contacts.adapter.ContactModel
import dark.composer.fakecallapp.databinding.FragmentHomeBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var sharedPref: EncryptedSharedPref

    override fun onViewCreate() {

        sharedPref = EncryptedSharedPref(requireContext())

        val list = mutableListOf<ContactModel>()
        list.add(
            ContactModel(
                "Character 1",
                "+1248754248",
                sharedPref.get(sharedPref.Key1, 0),
                true,
                false
            )
        )
        list.add(
            ContactModel(
                "Character 2", "+1248754248", sharedPref.get(sharedPref.Key2, 0), true, false
            )
        )
        list.add(
            ContactModel(
                "Character 3", "+1248754248", sharedPref.get(sharedPref.Key3, 0), false, false
            )
        )
        list.add(
            ContactModel(
                "Character 4", "+1248754248", sharedPref.get(sharedPref.Key4, 0), false, false
            )
        )
        list.add(
            ContactModel(
                "Character 5", "+1248754248", sharedPref.get(sharedPref.Key5, 0), false, false
            )
        )
        list.add(
            ContactModel(
                "Character 6", "+1248754248", sharedPref.get(sharedPref.Key6, 0), false, false
            )
        )

        sharedPref.setList(list)

//        lifecycleScope.launch {
//            list.forEachIndexed { index, contactModel ->
//                sharedPref.save(index.toString(), contactModel.count)
//            }
//        }
        loadRewardedAd()

        showAd()

        binding.call.setOnClickListener {
            showAd()
            navController.navigate(R.id.action_homeFragment_to_callFragment)
        }
        binding.videoCall.setOnClickListener {
            showAd()
            navController.navigate(R.id.action_homeFragment_to_videoFragment)
        }
        binding.live.setOnClickListener {
            showAd()
            navController.navigate(R.id.action_homeFragment_to_liveFragment)
        }
        binding.chat.setOnClickListener {
            showAd()
            navController.navigate(R.id.action_homeFragment_to_chatFragment)
        }
        binding.characters.setOnClickListener {
            showAd()
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
            var url = "https://play.google.com/store/apps/details?id=com.fakecallpoliceapplab"
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        binding.game.setOnClickListener {
            showAd()
            navController.navigate(
                R.id.action_homeFragment_to_webViewFragment,
                bundleOf("url" to "https://www.gamezop.com/?id=3178")
            )
        }
    }
}