package dark.composer.fakecallapp.video

import android.hardware.Camera
import android.view.SurfaceHolder
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentVideoAcceptBinding
import java.io.IOException

class VideoAcceptFragment :
    BaseFragment<FragmentVideoAcceptBinding>(FragmentVideoAcceptBinding::inflate),
    SurfaceHolder.Callback {
    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null

    override fun onViewCreate() {
        setupSurfaceHolder()

        binding.decline.setOnClickListener {
            navController.navigate(R.id.action_videoAcceptFragment_to_endingCallFragment)
        }

        val uri = "android.resource://" + requireActivity().packageName + "/" + R.raw.video22

        binding.video.setVideoPath(uri)

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