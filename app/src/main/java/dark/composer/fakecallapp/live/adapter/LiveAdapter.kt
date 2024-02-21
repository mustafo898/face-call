package dark.composer.fakecallapp.live.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.contacts.adapter.ContactModel
import dark.composer.fakecallapp.databinding.ItemLiveBinding
import dark.composer.fakecallapp.databinding.ItemMoreContactsBinding
import dark.composer.fakecallapp.gone
import dark.composer.fakecallapp.visible


class LiveAdapter(val context: Context) :
    RecyclerView.Adapter<LiveAdapter.ViewHolder>() {

    private var list = ArrayList<LiveModel>()

    fun set(list: List<LiveModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(data: LiveModel) {
        this.list.add(data)
        notifyItemInserted(list.size)
    }

    inner class ViewHolder(private val binding: ItemLiveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(data: LiveModel) {
            binding.image.setImageResource(data.image)
            binding.name.setText(data.name)
            binding.txt.text = data.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLiveBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(list[position])

    override fun getItemCount(): Int = list.size
}
