package com.soten.omdb.remote.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.soten.omdb.remote.api.MovieSearchApi
import com.soten.omdb.remote.interceptor.ApiInterceptor
import com.soten.omdb.remote.api.ApiInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val CONTENT_TYPE = "application/json"
    private const val LOG_TAG = "omdb movie Search"

    private val networkJson: Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(networkJson.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .client(
                provideOkHttpClient(
                    ApiInterceptor(),
                    HttpLoggingInterceptor { log ->
                        Log.d(LOG_TAG, log)
                    }.apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMediaSearchApi(): MovieSearchApi {
        return provideRetrofit().create(MovieSearchApi::class.java)
    }

    private fun provideOkHttpClient(vararg interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().run {
            interceptor.forEach { addInterceptor(it) }
            build()
        }
    }
}