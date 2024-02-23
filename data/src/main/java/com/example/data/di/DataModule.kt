package com.example.data.di

import com.example.data.operations.UsersRepositoryImp
import com.example.data.operations.UsersRetrofit
import com.example.domain.operations.UsersRepositoryInterface
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }

    private var mGsonConverterFactory: GsonConverterFactory? = null
    private val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverterFactory == null) {
                mGsonConverterFactory = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create()
                    )
            }
            return mGsonConverterFactory!!
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverter)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersRetrofit(retrofit: Retrofit): UsersRetrofit =
        retrofit.create(UsersRetrofit::class.java)

    @Provides
    @Singleton
    fun provideUsersRepository(usersRepositoryImp: UsersRepositoryImp): UsersRepositoryInterface =
        usersRepositoryImp
}