package com.junsu.sample.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.junsu.sample.Define
import com.junsu.sample.model.ItemDao
import com.junsu.sample.ui.list.paged.network.api.ItemService
import com.junsu.sample.util.apiInterceptor
import com.junsu.sample.util.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideItemDao(@ApplicationContext context: Context): ItemDao {
        return AppDatabase.getInstance(context).itemDao()
    }

    @Singleton
    @Provides
    fun provideItemService(@ApplicationContext context: Context): ItemService {
        return Retrofit.Builder()
            .baseUrl(Define.URL_HOST)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .apply {
                client(apiInterceptor)
            }
            .build().create(ItemService::class.java)
    }
}