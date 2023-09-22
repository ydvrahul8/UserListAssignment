package com.example.gitusers.domain.usecase

import com.example.gitusers.domain.remote.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val userRepository: UserRepository) {
     operator fun invoke() = userRepository.getUsers()
}