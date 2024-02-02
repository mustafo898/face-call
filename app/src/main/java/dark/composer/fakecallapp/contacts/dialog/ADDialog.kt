package dark.composer.fakecallapp.contacts.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import dark.composer.fakecallapp.databinding.AddDialogBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.visible

@SuppressLint("SetTextI18n")
class ADDialog(
    private val context: Context,
    private var count: Int,
    private var limit: Int,
    private val onItemClick: ((Int) -> Unit)
) : AlertDialog(context) {
    private val binding = AddDialogBinding.inflate(layoutInflater)

    fun loading(){
        binding.t2.gone()
        binding.progress.visible()
        binding.ad.isClickable = false
    }

    fun def(){
        binding.t2.visible()
        binding.progress.gone()
        binding.ad.isClickable = true
    }

    fun set(count: Int,limit: Int){
        binding.t3.text = "$count/$limit"
    }

    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)

        binding.t3.text = "${count++}/$limit"

        binding.exit.setOnClickListener {
            dismiss()
        }

        binding.ad.setOnClickListener {
            onItemClick.invoke(count)
        }
    }
}