package com.aiinty.dragonfly.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiinty.dragonfly.core.datastore.DataStoreInstance
import com.aiinty.dragonfly.core.entity.User
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    var user: User? = null
        private set

    fun updateUser(updatedUser: User) {
        user = updatedUser
    }

    fun loadUser(context: Context) {
        viewModelScope.launch {
            user = DataStoreInstance.readUser(context)
        }
    }
}