package com.example.gitusers.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.gitusers.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userListUseCase: GetUserListUseCase,
) : ViewModel() {

    val userList = userListUseCase().cachedIn(viewModelScope)

}