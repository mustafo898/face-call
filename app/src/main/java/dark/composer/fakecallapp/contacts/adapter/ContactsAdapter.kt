package dark.composer.fakecallapp.contacts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.ItemContactsBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.utl.EncryptedSharedPref
import dark.composer.fakecallapp.visible


class ContactsAdapter(val context: Context) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var list = ArrayList<ContactModel>()

    private var itemClickListener: ((Boolean, Int, Int) -> Unit)? =
        null

    fun setItemClickListener(f: (Boolean, Int, Int) -> Unit) {
        itemClickListener = f
    }

    fun set(list: List<ContactModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(data: ContactModel) {
        this.list.add(data)
        notifyItemInserted(list.size)
    }

    fun update(count: Int, position: Int) {
        this.list[position].count = count
        notifyItemChanged(position)
    }

    private var selected = -1

    val sharedPref: EncryptedSharedPref = EncryptedSharedPref(context)

    inner class ViewHolder(private val binding: ItemContactsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(data: ContactModel) {
            var c = sharedPref.get(layoutPosition.toString(), 0)
            binding.name.text = data.name
            binding.number.text = data.number
            val m = c + 1
            binding.count.text = m.toString()
            c
            binding.tick.setImageResource(if (data.clicked) R.drawable.tick else R.drawable.round)

            if (c <= 4 && data.isOpen) {
                binding.l3.gone()
                binding.tick.visible()
            } else {
                binding.tick.gone()
                binding.l3.visible()
            }

            binding.m2.setOnClickListener {
                Toast.makeText(binding.root.context, "$layoutPosition", Toast.LENGTH_SHORT).show()
                if (data.isOpen) {

                    when (selected) {
                        -1 -> {
                            data.clicked = !data.clicked
                            selected = layoutPosition
                        }

                        layoutPosition -> {
                            data.clicked = !data.clicked
                            selected = layoutPosition
                        }

                        else -> {
                            list[selected].clicked = false
                            list[layoutPosition].clicked = true
                            selected = layoutPosition
                        }
                    }

                    itemClickListener?.invoke(true, c, layoutPosition)

                } else {

//                    sharedPref.save(
//                        layoutPosition.toString(),
//                        c + 1
//                    )
                    if (c >= 4) {
                        data.isOpen = true
                    }
                    Log.d("sdfslkjslfjsldfkjs", "bindData: $c")
                    itemClickListener?.invoke(false, c, layoutPosition)
                }
                notifyDataSetChanged()
            }

            binding.count.text = "${c}/4"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContactsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(list[position])

    override fun getItemCount(): Int = list.size
}
