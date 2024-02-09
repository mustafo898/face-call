package dark.composer.fakecallapp.contacts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.databinding.ItemContactsBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.utl.EncryptedSharedPref
import dark.composer.fakecallapp.visible


class ContactsAdapter(val context: Context) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var list = ArrayList<ContactModel>()

    private var itemClickListener: ((Boolean, Int, Int, Int,Int) -> Unit)? = null

    fun setItemClickListener(f: (Boolean, Int, Int, Int,Int) -> Unit) {
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
            binding.name.setText(data.name)
            binding.number.text = data.number
            binding.count.text = data.count.toString()
            binding.tick.setImageResource(if (data.selected) R.drawable.tick else R.drawable.round)

            binding.image.setImageResource(data.image)

            if (data.selected) selected = layoutPosition

            if (data.count >= data.limit) {
                binding.l3.gone()
                binding.tick.visible()
                data.isOpen = true
            } else {
                binding.tick.gone()
                binding.l3.visible()
            }
            var last = selected

            binding.m2.setOnClickListener {
                if (data.isOpen) {

                    when (selected) {
                        -1 -> {
                            data.selected = !data.selected
                            selected = layoutPosition
                            last = selected
                        }

//                        layoutPosition -> {
//                            data.selected = !data.selected
//                            selected = layoutPosition
//                        }

                        else -> {
                            list[layoutPosition].selected = true
                            list[selected].selected = false
                            last = selected
                            list[layoutPosition].selected = true
                            selected = layoutPosition
                        }
                    }

                    itemClickListener?.invoke(true, data.count, layoutPosition, data.limit,last)

                } else {
                    itemClickListener?.invoke(false, data.count, layoutPosition, data.limit,last)
                }
                notifyDataSetChanged()
            }

            binding.count.text = "${data.count}/${data.limit}"
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContactsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(list[position])

    override fun getItemCount(): Int = list.size
}
