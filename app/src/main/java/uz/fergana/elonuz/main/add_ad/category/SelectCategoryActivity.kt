package uz.fergana.elonuz.main.add_ad.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import org.greenrobot.eventbus.EventBus
import uz.fergana.elonuz.adapters.CategoryAdapter
import uz.fergana.elonuz.adapters.CategoryAdapterCallback
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.data.models.EventModel
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.PrefUtils
import uz.fergana.elonuz.databinding.ActivitySelectCategoryBinding

class SelectCategoryActivity : AppCompatActivity(), CategoryAdapterCallback {
    lateinit var binding: ActivitySelectCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySelectCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dividerItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rv.addItemDecoration(dividerItemDecoration)
        binding.rv.adapter= CategoryAdapter(PrefUtils.getCategories(),this)
    }

    override fun onSelectCategory(item: CategoryModel) {
        EventBus.getDefault().post(EventModel(Constants.EVENT_SELECT_CATEGORY,item))
        finish()
    }
}