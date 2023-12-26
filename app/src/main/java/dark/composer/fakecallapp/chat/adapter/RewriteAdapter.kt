package dark.composer.fakecallapp.chat.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.databinding.ItemRewriteBinding

class RewriteAdapter(
    private val onItemClick: ((ChatModel) -> Unit)
) : RecyclerView.Adapter<RewriteAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val binding: ItemRewriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(data: ChatModel) {
            binding.t1.text = data.message

            binding.m1.setOnClickListener {
                onItemClick.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRewriteBinding.inflate(
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
