package com.aiinty.dragonfly.ui.screens.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.repositories.UserRepository
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var user: User? = null
        private set

    init {
        viewModelScope.launch {
            user = getUser()
        }
    }

    suspend fun getUser(): User? {
        return userRepository.getUser()
    }

    fun saveUser(user: User){
        viewModelScope.launch {
            userRepository.saveUser(user)
        }
    }
}
