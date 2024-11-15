package com.aiinty.dragonfly.ui.screens.welcome.loading

import androidx.lifecycle.ViewModel
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun getUser(): User? {
        return userRepository.getUser()
    }
}
