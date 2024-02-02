package dark.composer.fakecallapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dark.composer.fakecallapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Delay for 2 seconds (2000 milliseconds)
        Handler().postDelayed({
            // Start the main activity
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.`in`, R.anim.out)
            // Finish the splash activity
            finish()
        }, 1600)
    }
}