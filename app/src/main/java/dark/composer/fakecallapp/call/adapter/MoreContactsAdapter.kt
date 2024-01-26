package dark.composer.fakecallapp.call.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.contacts.adapter.ContactModel
import dark.composer.fakecallapp.databinding.ItemMoreContactsBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.visible


class MoreContactsAdapter(val context: Context) :
    RecyclerView.Adapter<MoreContactsAdapter.ViewHolder>() {

    private var list = ArrayList<ContactModel>()

    fun set(list: List<ContactModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(data: ContactModel) {
        this.list.add(data)
        notifyItemInserted(list.size)
    }

    inner class ViewHolder(private val binding: ItemMoreContactsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(data: ContactModel) {
            binding.image.setImageResource(data.image)
            if (data.isOpen){
                binding.block.gone()
                binding.txt.gone()
            }
            else{
                binding.txt.visible()
                binding.block.visible()
            }
            binding.txt.text = "${data.count}/${data.limit}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoreContactsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(list[position])

    override fun getItemCount(): Int = list.size
}
