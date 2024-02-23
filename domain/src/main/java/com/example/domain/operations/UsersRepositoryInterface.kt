package com.example.domain.operations

interface UsersRepositoryInterface {
    suspend fun getUsers(page: String, quantity: String, seed: String): List<UserParams>
}