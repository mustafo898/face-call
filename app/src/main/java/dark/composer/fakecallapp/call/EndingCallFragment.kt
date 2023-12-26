package dark.composer.fakecallapp.call

import android.os.Handler
import android.os.Looper
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentEndingCallBinding


class EndingCallFragment :
    BaseFragment<FragmentEndingCallBinding>(FragmentEndingCallBinding::inflate) {

    private val totalProgress = 100
    private val totalTimeInMillis = 3000L // 3 seconds
    private var progressStatus = 0
    private lateinit var handler: Handler

    override fun onViewCreate() {

        val total = 0
        binding.linearProgress.progress = total
        handler = Handler(Looper.getMainLooper())

        startHorizontalProgressAnimation()
    }

    override fun onBackPressed() {

    }

    private fun startHorizontalProgressAnimation() {
        Thread {
            val endTime = System.currentTimeMillis() + 3000
            while (System.currentTimeMillis() < endTime && progressStatus < 100) {
                progressStatus++
                // Update the progress bar on the main thread
                handler.post {
                    binding.linearProgress.progress = progressStatus
                }

                if (progressStatus == 99) {
                    handler.post {
                        navController.navigate(R.id.action_endingCallFragment_to_thanksForCallFragment)
                    }
                }

                // Delay for a short duration to mimic progress
                try {
                    Thread.sleep(30) // Adjust this value for desired animation speed
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
}