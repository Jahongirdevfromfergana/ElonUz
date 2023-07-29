package uz.fergana.elonuz.main.ads

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.permissionx.guolindev.PermissionX
import uz.fergana.elonuz.data.models.AdsModel
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.PrefUtils
import uz.fergana.elonuz.utils.loadImage
import uz.fergana.elonuz.R
import uz.fergana.elonuz.databinding.ActivityAdsDetailBinding

class AdsDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdsDetailBinding
    lateinit var adsModel: AdsModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT < 33) {
            adsModel = intent.getSerializableExtra(Constants.EXTRA_DATA) as AdsModel
        } else {
            adsModel =
                intent.getSerializableExtra(Constants.EXTRA_DATA, AdsModel::class.java)!!
        }
        binding.apply {
            imgAds.loadImage(adsModel.mainImage)
            tvTime.text = adsModel.date
            tvPrice.text = adsModel.price.toString() + " UZS"
            tvComment.text = adsModel.comment
            tvName.text = adsModel.name

            if (PrefUtils.checkFavorite(adsModel)) {
                iconFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                iconFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            btnCall.setOnClickListener {
                PermissionX.init(this@AdsDetailActivity)
                    .permissions(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_SMS
                    )
                    .request { allGranted, grantedList, deniedList ->
                        val dialIntent = Intent(Intent.ACTION_DIAL)
                        dialIntent.data = Uri.parse("tel:" + adsModel.phone)
                        startActivity(dialIntent)
                    }
            }

            btnSms.setOnClickListener {
                PermissionX.init(this@AdsDetailActivity)
                    .permissions(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_SMS
                    )
                    .request { allGranted, grantedList, deniedList ->
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("sms:" + adsModel.phone)
                        startActivity(intent)
                    }
            }

            btnFavorite.setOnClickListener {
                PrefUtils.setFavorites(adsModel)

                if (PrefUtils.checkFavorite(adsModel)) {
                    iconFavorite.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    iconFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
    }
}