package uz.fergana.elonuz.main.categories

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.adapters.AdsAdapter
import uz.fergana.elonuz.adapters.GridCategoryAdapter
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.devapp.elonuz.utils.Constants
import uz.fergana.elonuz.databinding.ActivityCategoriesBinding

class CategoriesActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoriesBinding
    lateinit var parentCategory: CategoryModel
    lateinit var viewModel: CategoriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            if (Build.VERSION.SDK_INT < 33) {
                parentCategory = intent.getSerializableExtra(Constants.EXTRA_DATA) as CategoryModel
            } else {
                parentCategory =
                    intent.getSerializableExtra(Constants.EXTRA_DATA, CategoryModel::class.java)!!
            }
            viewModel = ViewModelProvider(this@CategoriesActivity)[CategoriesViewModel::class.java]

            viewModel.errorLiveData.observe(this@CategoriesActivity) {
                Toast.makeText(this@CategoriesActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@CategoriesActivity) {
                swipe.isRefreshing = it
            }

            viewModel.categoriesListLiveData.observe(this@CategoriesActivity) {
                rvCategories.adapter =
                    GridCategoryAdapter(it.filter { it.parentId == parentCategory.id }, it)
            }

            viewModel.adsListLiveData.observe(this@CategoriesActivity) {
                rvAds.adapter = AdsAdapter(it)
            }

            swipe.setOnRefreshListener {
                loadData()
            }

            loadData()
        }
    }

    fun loadData() {
        viewModel.getCategories()
        viewModel.getAds(AdsFilter(category_id = parentCategory.id))
    }
}