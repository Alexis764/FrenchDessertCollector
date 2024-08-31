package edu.ws2024.a01.am.feature.username

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor() : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun onUsernameChanged(username: String) {
        hideIsError()

        if (username.length <= 20) {
            _username.value = username
        }
    }


    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun showIsError() {
        _isError.value = true
    }

    private fun hideIsError() {
        _isError.value = false
    }


}