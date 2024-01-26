package dark.composer.fakecallapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentSplashBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.visible

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private var progressStatus = 0
    private lateinit var handler: Handler

    override fun onViewCreate() {

        val total = 0
        binding.linearProgress.progress = total
        handler = Handler(Looper.getMainLooper())

        startHorizontalProgressAnimation()

        binding.next.setOnClickListener {
            navController.navigate(R.id.action_splashFragment_to_homeStartFragment)
        }

        binding.privacy.setOnClickListener {
            privacy()
        }
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
                binding.linearProgress.gone()
                binding.l1.visible()
            }
        }.start()
    }

    override fun onBackPressed() {

    }
}