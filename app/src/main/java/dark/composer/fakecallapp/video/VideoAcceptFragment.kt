package dark.composer.fakecallapp.video

import android.hardware.Camera
import android.os.Handler
import android.view.SurfaceHolder
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentVideoAcceptBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref
import java.io.IOException

class VideoAcceptFragment :
    BaseFragment<FragmentVideoAcceptBinding>(FragmentVideoAcceptBinding::inflate),
    SurfaceHolder.Callback {
    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null

    private var seconds = 0

    override fun onViewCreate() {
        setupSurfaceHolder()

        val sharedPref = EncryptedSharedPref(requireContext())

        binding.decline.setOnClickListener {
            navController.navigate(R.id.endingCallFragment)
        }

        var k = 0

        sharedPref.getList().forEachIndexed { index, contactModel ->
            if (contactModel.selected) k = index
        }

        val uri = when (k) {
            0 -> "android.resource://" + requireActivity().packageName + "/" + R.raw.video_call_character_1
            1 -> "android.resource://" + requireActivity().packageName + "/" + R.raw.video_call_character_2
            2 -> "android.resource://" + requireActivity().packageName + "/" + R.raw.video_call_character_3
            3 -> "android.resource://" + requireActivity().packageName + "/" + R.raw.video_call_character_4
            else -> "android.resource://" + requireActivity().packageName + "/" + R.raw.video_call_character_1
        }

        binding.video.setVideoPath(uri)

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

        binding.video.start()
        binding.video.setOnPreparedListener {
            it.isLooping = true
        }
    }

    override fun onStart() {
        binding.video.start()
        super.onStart()
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