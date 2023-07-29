package uz.fergana.elonuz.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.elonuz.data.models.AdsModel
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.fergana.elonuz.data.repository.UserRepository
import uz.fergana.elonuz.data.repository.sealed.DataResult
import uz.devapp.elonuz.utils.PrefUtils

class MainViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _categoriesListLiveData = MutableLiveData<List<CategoryModel>>()
    var categoriesListLiveData: LiveData<List<CategoryModel>> = _categoriesListLiveData

    private var _adsListLiveData = MutableLiveData<List<AdsModel>>()
    var adsListLiveData: LiveData<List<AdsModel>> = _adsListLiveData

    fun getCategories() {
        _progressLiveData.value = true
        viewModelScope.launch {
            val result = repository.getCategories()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    PrefUtils.setCategories(result.result)
                    _categoriesListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun getRegions() {
        viewModelScope.launch {
            val result = repository.getRegions()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    PrefUtils.setRegions(result.result)
                }
            }
        }
    }

    fun getAds(filter: AdsFilter) {
        viewModelScope.launch {
            val result = repository.getAds(filter)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _adsListLiveData.value = (result.result)
                }
            }
        }
    }
}