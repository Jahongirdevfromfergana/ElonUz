package uz.fergana.elonuz.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import uz.devapp.elonuz.utils.Constants
import uz.fergana.elonuz.R

fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(Constants.BASE_URL + url)
        .placeholder(R.drawable.placeholder)
        .into(this)
}