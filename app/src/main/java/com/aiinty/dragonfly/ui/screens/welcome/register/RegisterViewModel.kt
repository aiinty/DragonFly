package com.aiinty.dragonfly.ui.screens.welcome.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var user: User = User("", "", "")
        private set

    var currentRegisterScreenState by mutableStateOf(RegisterScreenState.START)

    fun nextRegisterScreenState(){
        currentRegisterScreenState = currentRegisterScreenState.nextState()
    }

    fun saveRegisteredUser(){
        viewModelScope.launch {
            userRepository.saveUser(user)
        }
    }
}