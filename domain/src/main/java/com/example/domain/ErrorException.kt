package com.example.domain

class ErrorException(val errorCode: Int, val errorMessage: String) : Exception(errorMessage)
