package com.example.gitusers.di

import com.example.gitusers.data.remote.UserAPI
import com.example.gitusers.data.repository.UserRepositoryImpl
import com.example.gitusers.domain.remote.UserRepository
import com.example.gitusers.util.Constants
import com.example.gitusers.util.NetworkConnectionInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideUserAPI(client: OkHttpClient): UserAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthClient(networkInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(networkInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(userAPI: UserAPI): UserRepository {
        return UserRepositoryImpl(userAPI)
    }
}