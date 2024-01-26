package dark.composer.fakecallapp.call

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentEndingCallBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.visible


class EndingCallFragment :
    BaseFragment<FragmentEndingCallBinding>(FragmentEndingCallBinding::inflate) {

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

    @SuppressLint("SetTextI18n")
    private fun startHorizontalProgressAnimation() {
        Thread {
            val endTime = System.currentTimeMillis() + 3000
            while (System.currentTimeMillis() < endTime && progressStatus <= 100) {
                progressStatus++
                handler.post {
                    binding.linearProgress.progress = progressStatus
                    binding.progress.text = "$progressStatus%"
                }

                try {
                    Thread.sleep(30)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            handler.post {
                navController.navigate(R.id.thanksForCallFragment)
            }
        }.start()
    }
}