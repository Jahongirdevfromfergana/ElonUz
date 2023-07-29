package uz.fergana.elonuz.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.elonuz.data.models.UserModel
import uz.fergana.elonuz.data.repository.UserRepository
import uz.fergana.elonuz.data.repository.sealed.DataResult

class ProfileViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _userListLiveData = MutableLiveData<UserModel>()
    var userListLiveData: LiveData<UserModel> = _userListLiveData


    fun getUser() {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.getUser()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _userListLiveData.value=(result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}