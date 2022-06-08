package com.mohammed.khurram.vmediademo.di

import android.content.Context
import com.mohammed.khurram.vmediademo.remote.RemoteAPI
import com.mohammed.khurram.vmediademo.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModules {
    /**
     * Hilt (Dagger) DI for retrofit object for remote data communication
     * Here we are using HTTP cache for local store of responses
     * using Interceptor for logging
     *
     * We could create multiple provider methods for each object but here I make one provider for simplicity
     */
    @Singleton
    @Provides
    fun provideRetrofitService(@ApplicationContext appContext: Context): RemoteAPI {
        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL).client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).cache(
                okhttp3.Cache(
                    File(appContext.applicationContext.cacheDir, "pokemon_cache"),
                    10 * 1024 * 1024
                )
            ).build()
        )
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RemoteAPI::class.java)
    }

}