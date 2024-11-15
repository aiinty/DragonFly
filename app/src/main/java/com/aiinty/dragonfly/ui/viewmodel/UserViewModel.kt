package com.aiinty.dragonfly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    var user: User? = null
        private set

    fun updateUser(updatedUser: User) {
        user = updatedUser
        viewModelScope.launch {
            userRepository.saveUser(updatedUser)
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            user = userRepository.getUser()
        }
    }
}