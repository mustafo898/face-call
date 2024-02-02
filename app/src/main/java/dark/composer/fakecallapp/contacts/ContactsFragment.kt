package dark.composer.fakecallapp.contacts

import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.contacts.adapter.ContactModel
import dark.composer.fakecallapp.contacts.adapter.ContactsAdapter
import dark.composer.fakecallapp.contacts.dialog.ADDialog
import dark.composer.fakecallapp.databinding.FragmentContactsBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {
    private lateinit var sharedPref: EncryptedSharedPref
    private var rewardedAd: RewardedAd? = null

    private val contactAdapter by lazy {
        ContactsAdapter(requireContext())
    }

    private lateinit var dialog: ADDialog

    private lateinit var list: List<ContactModel>

    private val TAG = "rororororor"
    override fun onBackPressed() {

    }

    override fun onViewCreate() {
        sharedPref = EncryptedSharedPref(requireContext())

        list = sharedPref.getList()

        binding.rv.adapter = contactAdapter

        loadRewardAd()

        contactAdapter.setItemClickListener { isOpen, count, pos, limit, last ->
            if (!isOpen) {
                dialog = ADDialog(requireContext(), count, limit) { count1 ->
                    showRewardAd(pos, count1)
                }
                dialog.show()
            } else {
                list[pos].selected = true
                list[pos].isOpen = true
                list[last].selected = false
                sharedPref.setList(list)
            }
        }

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_contactsFragment_to_homeFragment)
        }

        contactAdapter.set(sharedPref.getList())
    }

    private fun loadRewardAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(requireActivity(),
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("slsjsfjdlkjhfd", adError.toString())
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("slsjsfjdlkjhfd", "Ad was loaded. ${ad.responseInfo}")
                    rewardedAd = ad
//                    val options = ServerSideVerificationOptions.Builder()
//                        .setCustomData("SAMPLE_CUSTOM_DATA_STRING").build()
//
//                    rewardedAd!!.setServerSideVerificationOptions(options)
                }
            })
    }

    private fun showRewardAd(pos: Int, count: Int) {
        if (rewardedAd != null) {

            rewardedAd?.let { ad ->
                ad.show(requireActivity()) { rewardItem ->

                    list[pos].count = count
                    sharedPref.setList(list)
                    contactAdapter.update(count, pos)
                    rewardedAd = null
                }
            } ?: run {
                Log.d("ssdoeldffjslkdfjslkj", "showRewardAd: not ")
            }

            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                }

                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    loadRewardAd()
                    dialog.def()
                    if (list[pos].limit == list[pos].count)
                        dialog.dismiss()
                    else{
                        dialog.set(list[pos].count,list[pos].limit)
                    }
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    rewardedAd = null
                }

                override fun onAdImpression() {
                    Log.d(TAG, "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                }
            }
        } else {
            Toast.makeText(requireContext(), "Try again", Toast.LENGTH_SHORT).show()
            dialog.def()
            loadRewardAd()
        }

    }
}