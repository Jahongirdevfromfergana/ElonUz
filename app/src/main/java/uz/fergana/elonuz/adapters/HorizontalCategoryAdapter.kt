package uz.fergana.elonuz.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.main.ads.AdsActivity
import uz.fergana.elonuz.main.categories.CategoriesActivity
import uz.devapp.elonuz.utils.Constants
import uz.fergana.elonuz.utils.loadImage
import uz.fergana.elonuz.databinding.CategoryItemLayoutBinding

class HorizontalCategoryAdapter(val items:List<CategoryModel>, val fullItems:List<CategoryModel>):RecyclerView.Adapter<HorizontalCategoryAdapter.Vh>() {
    inner class Vh(val binding: CategoryItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val categoryModel = items[position]
        holder.itemView.setOnClickListener {
            val intent=Intent(it.context,if (fullItems.filter { it.parentId==categoryModel.id}.isEmpty()) AdsActivity::class.java else CategoriesActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,categoryModel)
            it.context.startActivity(intent)
        }
        holder.binding.tvCategory.text=categoryModel.title
        holder.binding.imgCategory.loadImage(categoryModel.image)
    }
}