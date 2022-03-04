package com.junsu.sample.di

import com.google.gson.GsonBuilder
import com.junsu.sample.BuildConfig
import com.junsu.sample.Define
import com.junsu.sample.ui.list.paged.network.api.ItemService
import com.junsu.sample.util.apiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy.ACCEPT_ORIGINAL_SERVER
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
//            DebugUtil.log(DebugLogType.API, message)
        }
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY //BASIC
        }
    }

    @Provides
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        val cookieManager = CookieManager(null, ACCEPT_ORIGINAL_SERVER)
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(logger)
            }
            addInterceptor(UserAgentInterceptor())
            cookieJar(JavaNetCookieJar(cookieManager))
        }.build()
    }

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Define.URL_HOST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .apply {
                client(apiInterceptor)
            }
            .build()

    @Provides
    @Singleton
    fun provideAppService(retrofit: Retrofit): ItemService =
        retrofit.create(ItemService::class.java)
}

private class UserAgentInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val requestWithUserAgent: Request = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(requestWithUserAgent)
    }
}
