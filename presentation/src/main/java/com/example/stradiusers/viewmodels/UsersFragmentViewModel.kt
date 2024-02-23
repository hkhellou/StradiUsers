package com.example.stradiusers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ErrorException
import com.example.domain.operations.GetUsersUseCase
import com.example.domain.operations.UserParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersFragmentViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) :
    ViewModel() {
    var itemMutableList: MutableLiveData<List<UserParams>> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var showLoader: MutableLiveData<Boolean> = MutableLiveData()
    private var listAux: List<UserParams> = emptyList()
    fun getUsers(page: String, quantity: String, seed : String) {
        viewModelScope.launch {
            try {
                val userList: List<UserParams> = getUsersUseCase.invoke(page, quantity, seed)
                listAux += userList
                itemMutableList.postValue(listAux)
                showLoader.postValue(false)
            } catch (error: ErrorException) {
                errorMessage.postValue(error.errorMessage)
            }
        }

    }
}