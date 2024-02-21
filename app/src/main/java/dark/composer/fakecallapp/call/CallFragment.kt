package dark.composer.fakecallapp.call

import android.media.MediaPlayer
import android.util.Log
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentCallBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref

class CallFragment : BaseFragment<FragmentCallBinding>(FragmentCallBinding::inflate) {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onViewCreate() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.call_bell)

        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }

        binding.accept.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.acceptCallFragment)
        }
        binding.decline.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.contactsFragment)
        }

        val sharedPref = EncryptedSharedPref(requireContext())

        sharedPref.getList().forEach { data ->
            if (data.selected) {
                binding.name.setText(data.name)
                binding.number.setText(data.number)

                binding.image.setImageResource(data.image)

                Log.d("sldkjskf", "onViewCreate: ${data.name}  ${data.number}")
            }
        }

    }

    override fun onBackPressed() {

    }
}