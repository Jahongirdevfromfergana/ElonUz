package uz.fergana.elonuz.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.elonuz.data.models.request.LoginRequest
import uz.fergana.elonuz.data.models.request.RegistrationRequest
import uz.fergana.elonuz.data.models.response.AuthResponse
import uz.fergana.elonuz.data.repository.UserRepository
import uz.fergana.elonuz.data.repository.sealed.DataResult
import uz.devapp.elonuz.utils.PrefUtils

class AuthViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _authListLiveData = MutableLiveData<AuthResponse>()
    var authListLiveData: LiveData<AuthResponse> = _authListLiveData

    fun registration(request: RegistrationRequest) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.registration(request)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    PrefUtils.setToken(result.result.token)
                    _authListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.login(request)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    PrefUtils.setToken(result.result.token)
                    _authListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}