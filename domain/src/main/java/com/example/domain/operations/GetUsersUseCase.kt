package com.example.domain.operations

import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val usersRepositoryInterface: UsersRepositoryInterface) {

    suspend operator fun invoke(page: String, quantity: String, seed: String): List<UserParams> {
        return usersRepositoryInterface.getUsers(page, quantity, seed)
    }
}