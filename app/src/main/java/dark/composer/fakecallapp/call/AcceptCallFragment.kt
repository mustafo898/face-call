package dark.composer.fakecallapp.call

import android.os.Handler
import android.widget.SeekBar
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentAcceptCallBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AcceptCallFragment :
    BaseFragment<FragmentAcceptCallBinding>(FragmentAcceptCallBinding::inflate) {

    private var seconds = 0

    override fun onViewCreate() {

        binding.decline.setOnClickListener {
            navController.navigate(R.id.action_acceptCallFragment_to_endingCallFragment)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> binding.audioMic.setImageResource(R.drawable.audio_0)
                    1 -> binding.audioMic.setImageResource(R.drawable.audio_25)
                    2 -> binding.audioMic.setImageResource(R.drawable.audio_50)
                    3 -> binding.audioMic.setImageResource(R.drawable.audio)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
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
            navController.navigate(R.id.action_acceptCallFragment_to_contactsFragment)
        }
        binding.chat.setOnClickListener {
            navController.navigate(R.id.action_acceptCallFragment_to_chatFragment)
        }
        binding.videoCall.setOnClickListener {
            navController.navigate(R.id.action_acceptCallFragment_to_videoAcceptFragment)
        }
    }

    override fun onBackPressed() {

    }
}