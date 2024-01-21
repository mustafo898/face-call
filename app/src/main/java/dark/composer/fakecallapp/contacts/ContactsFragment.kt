package dark.composer.fakecallapp.contacts

import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.contacts.adapter.ContactsAdapter
import dark.composer.fakecallapp.contacts.dialog.ADDialog
import dark.composer.fakecallapp.databinding.FragmentContactsBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate),
    ADDialog.DeleteChekListener {
    private var rewardedAd: RewardedAd? = null
    private lateinit var sharedPref: EncryptedSharedPref

    private val contactAdapter by lazy {
        ContactsAdapter(requireContext())
    }

    override fun onViewCreate() {
        loadRewardedAd1()
        sharedPref = EncryptedSharedPref(requireContext())


        binding.rv.adapter = contactAdapter


        contactAdapter.setItemClickListener { isOpen, count, pos ->
            if (!isOpen) {
                val dialog = ADDialog(requireContext())
                dialog.getCount(count, pos)
                dialog.show()
            }
        }

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_contactsFragment_to_homeFragment)
        }

//        val list = mutableListOf<ContactModel>()
//        list.add(ContactModel("Character 1", "+1248754248", 0, true, false))
//        list.add(ContactModel("Character 2", "+1248754248", 0, true, false))
//        list.add(ContactModel("Character 3", "+1248754248", 0, false, false))
//        list.add(ContactModel("Character 4", "+1248754248", 0, false, false))
//        list.add(ContactModel("Character 5", "+1248754248", 0, false, false))
//        list.add(ContactModel("Character 6", "+1248754248", 0, false, false))

        contactAdapter.set(sharedPref.getList())
    }

    fun loadRewardedAd1() {
        val adRequest = AdRequest.Builder().build()

        Log.e("FABKFA", "loadRewardedAd: ${rewardedAd?.adUnitId}")
        RewardedAd.load(requireActivity(),
            "ca-app-pub-7173802867165820/3073425903",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    Toast.makeText(requireContext(), "ad's loaded", Toast.LENGTH_SHORT).show()
                    Log.e("FABKFA", "onAdFailedToLoad: {ad's loaded}")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Toast.makeText(requireContext(), "failed to load", Toast.LENGTH_SHORT).show()
                    Log.e("FABKFA", "onAdFailedToLoad: ${loadAdError.cause}")
                    Log.e("FABKFA", "onAdFailedToLoad: ${loadAdError.message}")
                }
            })
    }

    fun showAd1(count: Int, post: Int) {
        rewardedAd?.let {
            it.show(requireActivity()) { rewardItem ->
                // Handle the reward
                var count1 = count
                sharedPref.save(post.toString(), ++count1)
                contactAdapter.update(count, post)
                Toast.makeText(requireContext(), "ad's loaded", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            // Ad not loaded, handle accordingly
            loadRewardedAd1()
            Toast.makeText(requireContext(), "ad not loaded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDeleteItem(count: Int, pos: Int) {
        showAd1(count = count, post = pos)
    }
}