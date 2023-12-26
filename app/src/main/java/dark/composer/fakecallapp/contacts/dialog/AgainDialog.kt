package dark.composer.fakecallapp.contacts.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import dark.composer.fakecallapp.databinding.ChooseDialogBinding

@SuppressLint("SetTextI18n")
class AgainDialog(
    private val context: Context,
    private val onItemClick: ((Int) -> Unit)
) : AlertDialog(context) {
    private val binding = ChooseDialogBinding.inflate(layoutInflater)

    private var count = 0
    private var pos = 0

    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(true)

        binding.chat.setOnClickListener {
            onItemClick.invoke(0)
            dismiss()
        }
        binding.live.setOnClickListener {
            onItemClick.invoke(1)
            dismiss()
        }
        binding.call.setOnClickListener {
            onItemClick.invoke(2)
            dismiss()
        }
        binding.videoCall.setOnClickListener {
            onItemClick.invoke(3)
            dismiss()
        }
    }
}