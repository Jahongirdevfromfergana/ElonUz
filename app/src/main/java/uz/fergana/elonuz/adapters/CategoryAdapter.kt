package uz.fergana.elonuz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.databinding.SelectCategoryItemLayoutBinding

interface CategoryAdapterCallback {
    fun onSelectCategory(item: CategoryModel)
}

class CategoryAdapter(val items: List<CategoryModel>, val callback: CategoryAdapterCallback) :
    RecyclerView.Adapter<CategoryAdapter.Vh>() {
    inner class Vh(val binding: SelectCategoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            SelectCategoryItemLayoutBinding.inflate(
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
            callback.onSelectCategory(item)
        }
        holder.binding.tvName.text = item.title
    }
}