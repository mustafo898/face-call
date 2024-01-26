package dark.composer.fakecallapp.video

import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.MediaPlayer
import android.os.Build
import android.view.SurfaceHolder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.FragmentVideoBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref
import java.io.IOException

class VideoFragment : BaseFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate),
    SurfaceHolder.Callback {

    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null
    private val neededPermissions = arrayOf(CAMERA)
    private lateinit var mediaPlayer: MediaPlayer

    override fun onViewCreate() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.video_call_bell)

        val sharedPref = EncryptedSharedPref(requireContext())

        sharedPref.getList().forEachIndexed { i, s ->
            if (s.isOpen) {
                binding.image.setImageDrawable(ContextCompat.getDrawable(requireContext(), s.image))
                binding.name.text = s.name
                binding.number.text = s.number
            }
        }

        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }

        val result = checkPermission()
        if (result) {
            setupSurfaceHolder()
        }

        binding.accept.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.videoAcceptFragment)
        }
        binding.decline.setOnClickListener {
            mediaPlayer.release()
            navController.navigate(R.id.contactsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    @SuppressLint("MissingSuperCall")
    override fun onDetach() {
        mediaPlayer.release()
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