package com.example.gitusers.domain.remote

import androidx.paging.PagingData
import com.example.gitusers.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<PagingData<User>>


}