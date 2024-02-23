package com.example.data.operations

import com.example.domain.ErrorException
import com.example.domain.operations.UserParams
import com.example.domain.operations.UsersRepositoryInterface
import java.io.IOException
import javax.inject.Inject

class UsersRepositoryImp @Inject constructor(private val usersRetrofit: UsersRetrofit) :
    UsersRepositoryInterface {
    override suspend fun getUsers(page : String,quantity: String,seed : String): List<UserParams> {
        try {
            val response = usersRetrofit.getUsers(page,quantity,seed)
            return if (response.isSuccessful) {
                response.body()?.results ?: emptyList()
            } else {
                val error = response.code()
                val errorMessage = response.message() ?: "Error Desconocido"
                throw ErrorException(error, errorMessage)
            }
        } catch (e: IOException) {
            throw ErrorException(-1, "Error de conexión: ${e.message}")
        } catch (e: Exception) {
            throw ErrorException(-1, "Error inesperado: ${e.message}")
        }
    }
}