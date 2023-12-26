package dark.composer.fakecallapp.chat.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.databinding.ItemChatBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.visible

class ChatAdapter(
    private val onItemClick: ((String) -> Unit)
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var list = ArrayList<ChatModel>()

    fun set(list: List<ChatModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(data: ChatModel) {
        this.list.add(data)
        notifyItemInserted(list.size)
    }

    fun delete() {
        this.list.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(data: ChatModel) {
            if (data.sender) {
                binding.m1.gone()
                binding.m2.visible()
                binding.t2.text = data.message
            } else {
                binding.m2.gone()
                binding.m1.visible()
                binding.t1.text = data.receiveMessage
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatBinding.inflate(
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
