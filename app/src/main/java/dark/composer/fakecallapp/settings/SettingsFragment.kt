package dark.composer.fakecallapp.settings

import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val adapter by lazy {
        SettingsAdapter {
            when (it) {
                0 -> license()

                1 -> privacy()

                2 -> rate()

                3 -> share()

                4 -> moreGame()

                5 -> game()
            }
        }
    }

    override fun onBackPressed() {

    }

    override fun onViewCreate() {
        val list = mutableListOf<SettingsModel>()
        binding.rv.adapter = adapter
        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        list.add(SettingsModel("License", R.drawable.license))
        list.add(SettingsModel("Privacy Policy", R.drawable.privacy))
        list.add(SettingsModel("Rate us", R.drawable.like))
        list.add(SettingsModel("Share it", R.drawable.share))
        list.add(SettingsModel("More Games", R.drawable.more))
        list.add(SettingsModel("Play Games", R.drawable.game))

        adapter.set(list)

        binding.back.setOnClickListener {
            navController.navigate(R.id.homeStartFragment)
        }
    }
}