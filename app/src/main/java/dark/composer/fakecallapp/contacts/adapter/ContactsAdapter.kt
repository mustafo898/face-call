package dark.composer.fakecallapp.contacts.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.ItemContactsBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.visible

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val binding: ItemContactsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(data: ContactModel) {
            binding.name.text = data.name
            binding.number.text = data.number
            val m = data.count + 1
            binding.count.text = m.toString()


            binding.tick.setImageResource(if (data.clicked) R.drawable.tick else R.drawable.round)

            if (data.count <= 4 && data.isOpen) {
                binding.l3.gone()
                binding.tick.visible()
            } else {
                binding.tick.gone()
                binding.l3.visible()
            }

            binding.m2.setOnClickListener {
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

                    itemClickListener?.invoke(true, data.count, layoutPosition)

                } else {
                    data.count++
                    if (data.count == 4) {
                        data.isOpen = true
                    }
                    val f = data.count - 1
                    Log.d("sdfslkjslfjsldfkjs", "bindData: $f")
                    itemClickListener?.invoke(false, f, layoutPosition)
                }
                notifyDataSetChanged()
            }

            binding.count.text = "${data.count}/4"
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
