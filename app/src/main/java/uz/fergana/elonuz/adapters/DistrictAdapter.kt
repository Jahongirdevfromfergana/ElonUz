package uz.fergana.elonuz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.elonuz.data.models.DistrictModel
import uz.fergana.elonuz.databinding.DistrictItemLayoutBinding

class DistrictAdapter(val items: List<DistrictModel>, val callback: RegionAdapterCallback) : RecyclerView.Adapter<DistrictAdapter.Vh>() {
    inner class Vh(val binding: DistrictItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            DistrictItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            callback.onSelectDistrict(item)
        }
        holder.binding.tvName.text=item.nameUz
    }
}