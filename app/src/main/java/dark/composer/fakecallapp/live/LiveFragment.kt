package dark.composer.fakecallapp.live

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Handler
import android.view.SurfaceHolder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentLiveBinding
import dark.composer.fakecallapp.live.adapter.LiveAdapter
import dark.composer.fakecallapp.live.adapter.LiveModel
import java.io.IOException

class LiveFragment : BaseFragment<FragmentLiveBinding>(FragmentLiveBinding::inflate),
    SurfaceHolder.Callback {

    private val liveAdapter by lazy {
        LiveAdapter(requireContext())
    }

    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null
    private val neededPermissions = arrayOf(Manifest.permission.CAMERA)
    private var seconds = 0
    private lateinit var handler: Handler
    private var count = 0
    val list = ArrayList<LiveModel>()


    private val messageRunnable = object : Runnable {
        override fun run() {
            addItemsWithDelay(list,0)
            handler.postDelayed(this, 3000) // Add a message every 3 seconds (adjust as needed)
        }
    }
    override fun onViewCreate() {

//        val result = checkPermission()
//        if (result) {
        setupSurfaceHolder()
//        }

        handler = Handler()

        binding.rv.adapter = liveAdapter


        list.add(LiveModel("Cool that I found this!", R.drawable.main, "Bob"))
        list.add(LiveModel("Hope to see you again soon!", R.drawable.c2, "Bugs"))
        list.add(LiveModel("How are you?", R.drawable.c3, "Rocky"))
        list.add(LiveModel("I've heard so much about you", R.drawable.c4, "Luna"))
        list.add(LiveModel("What's up?", R.drawable.main, "Bob"))
        list.add(LiveModel("Thanks for having me", R.drawable.c2, "Bugs"))
        list.add(LiveModel("How is everything?", R.drawable.c3, "Rocky"))
        list.add(LiveModel("Hi", R.drawable.c4, "Luna"))


        binding.game.setOnClickListener {
            game()
        }

        handler.post(messageRunnable)

        addItemsWithDelay(list, 0)

        changeTextWithDelay()

        binding.decline.setOnClickListener {
            navController.navigate(R.id.endingCallFragment)
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
    }

    private fun addItemsWithDelay(items: List<LiveModel>, index: Int) {
        if (index < items.size) {
            // Add item to the list after 3 seconds
            handler.postDelayed({
                val newItem = items[index]
                liveAdapter.add(newItem)

                // Print the updated list

                // Schedule the next item after the delay
                addItemsWithDelay(items, index + 1)
                binding.rv.smoothScrollToPosition(items.lastIndex)

            }, 2000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove any remaining callbacks from the handler
        handler.removeCallbacksAndMessages(null)
    }

    private fun changeTextWithDelay() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                // Change the text every 500 seconds
                count += (20..10000).random()
                binding.count.text = count.toString()

                // Schedule the next text change after 500 seconds
                handler.postDelayed(this, 750) // 750 seconds in milliseconds
            }
        }, 750) // Initial delay before the first text change
    }

    private fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            val permissionsNotGranted = ArrayList<String>()
            for (permission in neededPermissions) {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(), permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsNotGranted.add(permission)
                }
            }
            if (permissionsNotGranted.size > 0) {
                var shouldShowAlert = false
                for (permission in permissionsNotGranted) {
                    shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(), permission
                    )
                }
                val arr = arrayOfNulls<String>(permissionsNotGranted.size)
                val permissions = permissionsNotGranted.toArray(arr)
                if (shouldShowAlert) {
                    requestPermissions(permissions)
                } else {
                    requestPermissions(permissions)
                }
                return false
            }
        }
        return true
    }

    private fun requestPermissions(permissions: Array<String?>) {
        ActivityCompat.requestPermissions(requireActivity(), permissions, 21)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            21 -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        //t
                        return
                    }
                }
                setupSurfaceHolder()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupSurfaceHolder() {
        surfaceHolder = binding.preview.holder
        binding.preview.holder.addCallback(this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        startCamera()
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
        resetCamera()
    }

    private fun resetCamera() {
        if (surfaceHolder!!.surface == null) {
            return
        }
        camera!!.stopPreview()
        try {
            camera!!.setPreviewDisplay(surfaceHolder)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        camera!!.startPreview()
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        releaseCamera()
    }

    private fun startCamera() {
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
        camera!!.setDisplayOrientation(90)
        try {
            camera!!.setPreviewDisplay(surfaceHolder)
            camera!!.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun releaseCamera() {
        camera!!.stopPreview()
        camera!!.release()
        camera = null
    }

    override fun onBackPressed() {

    }
}