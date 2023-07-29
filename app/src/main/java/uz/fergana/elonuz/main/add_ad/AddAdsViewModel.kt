package uz.fergana.elonuz.main.add_ad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.elonuz.data.repository.UserRepository
import uz.fergana.elonuz.data.repository.sealed.DataResult

class AddAdsViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _addAdsListLiveData = MutableLiveData<Boolean>()
    var addAdsListLiveData: LiveData<Boolean> = _addAdsListLiveData

    fun addAds(
        mainImage: String,
        title: String,
        comment: String,
        address: String,
        phone: String,
        price: Double,
        categoryId: Int,
        districtId: Int
    ) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.addAds(
                mainImage,
                title,
                comment,
                address,
                phone,
                price,
                categoryId,
                districtId
            )
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _addAdsListLiveData.value = true
                }
            }
            _progressLiveData.value = false
        }
    }
}