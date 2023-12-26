package dark.composer.fakecallapp.settings

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dark.composer.fakecallapp.databinding.ItemSettingsBinding

class SettingsAdapter(
    private val onItemClick: ((Int) -> Unit)
) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    private var list = ArrayList<SettingsModel>()

    fun set(list: List<SettingsModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(data: SettingsModel) {
        this.list.add(data)
        notifyItemInserted(list.size)
    }

    fun delete() {
        this.list.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(data: SettingsModel) {
            binding.image.setImageResource(data.img)
            binding.txt.text = data.name
            binding.card.setOnClickListener {
                onItemClick.invoke(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSettingsBinding.inflate(
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
