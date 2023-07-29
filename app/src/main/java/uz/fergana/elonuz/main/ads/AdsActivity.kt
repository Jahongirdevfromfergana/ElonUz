package uz.fergana.elonuz.main.ads

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.adapters.AdsAdapter
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.devapp.elonuz.utils.Constants
import uz.fergana.elonuz.databinding.ActivityAdsBinding

class AdsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdsBinding
    lateinit var viewModel: AdsViewModel
    lateinit var parentCategory: CategoryModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            if (Build.VERSION.SDK_INT < 33) {
                parentCategory = intent.getSerializableExtra(Constants.EXTRA_DATA) as CategoryModel
            } else {
                parentCategory =
                    intent.getSerializableExtra(Constants.EXTRA_DATA, CategoryModel::class.java)!!
            }
            viewModel = ViewModelProvider(this@AdsActivity)[AdsViewModel::class.java]

            viewModel.errorLiveData.observe(this@AdsActivity) {
                Toast.makeText(this@AdsActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@AdsActivity) {
                swipe.isRefreshing = it
            }

            viewModel.adsListLiveData.observe(this@AdsActivity) {
                rv.adapter = AdsAdapter(it)
                lottie.visibility = if (it!!.isNotEmpty()) View.GONE else View.VISIBLE
            }

            swipe.setOnRefreshListener {
                loadData()
            }
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getAds(AdsFilter(category_id = parentCategory.id))
    }
}