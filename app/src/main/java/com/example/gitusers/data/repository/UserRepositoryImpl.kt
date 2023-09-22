package com.example.gitusers.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.gitusers.data.UserPagingSource
import com.example.gitusers.data.model.toUser
import com.example.gitusers.data.remote.UserAPI
import com.example.gitusers.domain.model.User
import com.example.gitusers.domain.remote.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userAPI: UserAPI) : UserRepository {
    override fun getUsers(): Flow<PagingData<User>> =
        Pager(config = PagingConfig(
            pageSize = 10,
            maxSize = 100
        ),
            pagingSourceFactory = { UserPagingSource(userAPI) }
        ).flow.map { data ->
            data.map {
                it.toUser()
            }
        }

}