package uz.fergana.elonuz.main.ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.elonuz.data.models.AdsModel
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.fergana.elonuz.data.repository.UserRepository
import uz.fergana.elonuz.data.repository.sealed.DataResult

class AdsViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _adsListLiveData = MutableLiveData<List<AdsModel>>()
    var adsListLiveData: LiveData<List<AdsModel>> = _adsListLiveData

    fun getAds(filter: AdsFilter) {
        viewModelScope.launch {
            _progressLiveData.value=true
            val result = repository.getAds(filter)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _adsListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value=false
        }
    }
}