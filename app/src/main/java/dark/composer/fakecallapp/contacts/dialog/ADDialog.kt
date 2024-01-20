package dark.composer.fakecallapp.contacts.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import dark.composer.fakecallapp.databinding.AddDialogBinding

@SuppressLint("SetTextI18n")
class ADDialog(
    private val context: Context,
    private val onItemClick: ((Int, Int) -> Unit)
) : AlertDialog(context) {
    private val binding = AddDialogBinding.inflate(layoutInflater)

    private var count = 0
    private var pos = 0

    fun getCount(count: Int, pos: Int) {
        this.count = count
        this.pos = pos
        Log.d("ssdsjfslkfsd", "getCount: ${this.count}/4")
        binding.t3.text = "${++this.count}/4"
    }

    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)

        binding.t3.text = "${count}/4"

        ++count

        binding.exit.setOnClickListener {
            onItemClick.invoke(count, pos)
            dismiss()
        }

        binding.ad.setOnClickListener {
            onItemClick.invoke(count++, pos)
            dismiss()
        }
    }
}