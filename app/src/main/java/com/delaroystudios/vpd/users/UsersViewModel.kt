package com.delaroystudios.vpd.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delaroystudios.domain.users.*
import com.delaroystudios.vpd.common.Event
import com.delaroystudios.vpd.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(getUsersUseCase: GetUsersUseCase,
                                             private val fetchUsersUseCase: FetchUsersUseCase,
                                             private val updateImageUseCase: UpdateImageUseCase,
                                             private val addToUsersUseCase: AddToUsersUseCase): ViewModel(){
    private val mutableState = MutableStateFlow<UiState>(UiState.Nothing)
    val userState = mutableState.asStateFlow()

    val users = getUsersUseCase.invoke(Unit)

    init {
        fetchUsersFromServer()
    }

    private fun fetchUsersFromServer() {
        viewModelScope.launch {
            mutableState.value = UiState.Loading
            val userResult = fetchUsersUseCase(Unit)

            if (userResult.isSuccess()) {
                mutableState.value = UiState.Success
            } else {
                mutableState.value = UiState.Error(errorMessage = Event(userResult.message))
            }
        }
    }

    fun updateImageuri(imageUri: String, id: Int) {
        viewModelScope.launch {
            updateImageUseCase.invoke(param = UpdateAddParam(imageUri = imageUri, id = id))
        }
    }


    fun addUser(id: Int,
                name: String,
                username: String,
                email: String,
                phone: String,
                website: String,
                imageUri: String

    ) {
        viewModelScope.launch {
            addToUsersUseCase.invoke(
                param = UsersP(
                    id = id,
                    name = name,
                    username = username,
                    email = email,
                    phone = phone,
                    website = website,
                    imageUri = imageUri
                )
            )
        }
    }
}
