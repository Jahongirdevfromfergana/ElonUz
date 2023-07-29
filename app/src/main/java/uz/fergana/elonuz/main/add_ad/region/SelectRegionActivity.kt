package uz.fergana.elonuz.main.add_ad.region

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import org.greenrobot.eventbus.EventBus
import uz.fergana.elonuz.adapters.RegionAdapter
import uz.fergana.elonuz.adapters.RegionAdapterCallback
import uz.fergana.elonuz.data.models.DistrictModel
import uz.fergana.elonuz.data.models.EventModel
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.PrefUtils
import uz.fergana.elonuz.databinding.ActivitySelectRegionBinding

class SelectRegionActivity : AppCompatActivity(), RegionAdapterCallback {
    lateinit var binding: ActivitySelectRegionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySelectRegionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dividerItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rv.addItemDecoration(dividerItemDecoration)
        binding.rv.adapter= RegionAdapter(PrefUtils.getRegions(),this)
    }

    override fun onSelectDistrict(item: DistrictModel) {
        EventBus.getDefault().post(EventModel(Constants.EVENT_SELECT_REGION,item))
        finish()
    }
}