package dark.composer.fakecallapp.call

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentAcceptCallBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref

class AcceptCallFragment :
    BaseFragment<FragmentAcceptCallBinding>(FragmentAcceptCallBinding::inflate) {

    private var seconds = 0
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager

    override fun onViewCreate() {
        val sharedPref = EncryptedSharedPref(requireContext())
        audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        var k = 0
        sharedPref.getList().forEachIndexed { i, s ->
            if (s.selected) {
                k = i
                binding.image.setImageDrawable(ContextCompat.getDrawable(requireContext(), s.image))
                binding.name.setText(s.name)
                binding.number.setText(s.number)
            }
        }

        mediaPlayer = MediaPlayer.create(
            requireContext(), when (k) {
                0 -> R.raw.voice_call_character_1
                1 -> R.raw.voice_call_character_2
                2 -> R.raw.voice_call_character_3
                3 -> R.raw.voice_call_character_4
                else -> R.raw.voice_call_character_1
            }
        )

        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }

        binding.decline.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.endingCallFragment)
        }

        var e = 0
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> {
                        binding.audioMic.setImageResource(R.drawable.audio_0)
                        setMinVolume()
                    }

                    1 -> {
                        binding.audioMic.setImageResource(R.drawable.audio_25)
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 3, 0)
                    }

                    2 -> {
                        binding.audioMic.setImageResource(R.drawable.audio_50)
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 7, 0)

                    }

                    3 -> {
                        binding.audioMic.setImageResource(R.drawable.audio)
                        setMaxVolume()
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                e = seekBar.progress
            }
        })

        var r = false
        binding.mic.setOnClickListener {
            if (r) {
                r = false
                binding.micImage.setImageResource(R.drawable.mic)
            } else {
                r = true
                binding.micImage.setImageResource(R.drawable.off_mic)
            }
        }

        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60

                val time = String.format("%02d:%02d", minutes, secs)
                binding.time.text = time

                seconds++

                handler.postDelayed(this, 1000)
            }
        })

        binding.character.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.action_acceptCallFragment_to_contactsFragment)
        }
        binding.chat.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.action_acceptCallFragment_to_chatFragment)
        }
        binding.videoCall.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.action_acceptCallFragment_to_videoAcceptFragment)
        }
    }

    private fun setMaxVolume() {
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)
    }

    private fun setMinVolume() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
    }

    override fun onStop() {
        mediaPlayer.release()
        super.onStop()
    }

    override fun onBackPressed() {

    }
}