package uz.fergana.elonuz.main.add_ad

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.permissionx.guolindev.PermissionX
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.data.models.DistrictModel
import uz.fergana.elonuz.data.models.EventModel
import uz.fergana.elonuz.main.MainActivity
import uz.fergana.elonuz.main.add_ad.category.SelectCategoryActivity
import uz.fergana.elonuz.main.add_ad.region.SelectRegionActivity
import uz.devapp.elonuz.utils.Constants
import uz.fergana.elonuz.databinding.ActivityAddAdsBinding

class AddAdsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddAdsBinding
    lateinit var viewModel: AddAdsViewModel
    var selectedImagePath = ""
    var selectedDistrict: DistrictModel? = null
    var selectedCategory: CategoryModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AddAdsViewModel::class.java]

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        binding.apply {

            viewModel.errorLiveData.observe(this@AddAdsActivity) {
                Toast.makeText(this@AddAdsActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@AddAdsActivity) {
                flProgress.visibility = if (it) View.VISIBLE else View.GONE
            }

            viewModel.addAdsListLiveData.observe(this@AddAdsActivity) {
                Toast.makeText(this@AddAdsActivity, "E'lon qo'shildi", Toast.LENGTH_SHORT).show()
                val i = Intent(this@AddAdsActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

            imgBack.setOnClickListener {
                finish()
            }

            imgAds.setOnClickListener {
                PermissionX.init(this@AddAdsActivity)
                    .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    .request { allGranted, grantedList, deniedList ->
                        ImagePicker.with(this@AddAdsActivity)
                            .provider(ImageProvider.BOTH)
                            .crop()
                            .createIntentFromDialog { launcher.launch(it) }
                    }
            }

            edRegion.setOnClickListener {
                startActivity(Intent(this@AddAdsActivity, SelectRegionActivity::class.java))
            }

            edCategory.setOnClickListener {
                startActivity(Intent(this@AddAdsActivity, SelectCategoryActivity::class.java))
            }

            btnSave.setOnClickListener {
                if (selectedImagePath.isNotEmpty() && selectedCategory != null && selectedDistrict != null && edName.text!!.isNotEmpty()) {
                    viewModel.addAds(
                        selectedImagePath,
                        edName.text.toString(),
                        edComment.text.toString(),
                        edAddress.text.toString(),
                        edPhone.text.toString(),
                        edPrice.text.toString().toDouble(),
                        selectedCategory!!.id,
                        selectedDistrict!!.id
                    )
                } else {
                    Toast.makeText(
                        this@AddAdsActivity,
                        "Iltimos hamma maydonlarni to'ldiring!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                uri.let { galleryUri ->
                    selectedImagePath = galleryUri.path ?: ""
                    Glide.with(this)
                        .load(galleryUri)
                        .into(binding.imgAds)
                }
            }
        }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: EventModel) {
        if (event.command == Constants.EVENT_SELECT_REGION) {
            selectedDistrict = event.data as DistrictModel
            binding.edRegion.setText(selectedDistrict?.nameUz)
        }
        if (event.command == Constants.EVENT_SELECT_CATEGORY) {
            selectedCategory = event.data as CategoryModel
            binding.edCategory.setText(selectedCategory?.title)
        }
    }
}