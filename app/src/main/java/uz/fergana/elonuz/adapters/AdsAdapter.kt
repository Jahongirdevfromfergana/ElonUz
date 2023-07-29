package uz.fergana.elonuz.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.elonuz.data.models.AdsModel
import uz.fergana.elonuz.main.ads.AdsDetailActivity
import uz.devapp.elonuz.utils.Constants
import uz.fergana.elonuz.utils.loadImage
import uz.fergana.elonuz.databinding.AdsItemLayoutBinding

class AdsAdapter(val items: List<AdsModel>) : RecyclerView.Adapter<AdsAdapter.Vh>() {
    inner class Vh(val binding: AdsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(AdsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val adsModel = items[position]
        holder.itemView.setOnClickListener {
            val intent= Intent(it.context, AdsDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,adsModel)
           it.context.startActivity(intent)
        }
        holder.binding.imgAds.loadImage(adsModel.mainImage)
        holder.binding.tvName.text = adsModel.name
        holder.binding.tvAddress.text = adsModel.address
        holder.binding.tvPrice.text = "${adsModel.price} UZS"
    }
}