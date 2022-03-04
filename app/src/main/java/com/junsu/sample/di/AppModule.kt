package com.junsu.sample.di

import android.content.Context
import com.junsu.sample.SharedData
import com.junsu.sample.model.ItemDao
import com.junsu.sample.util.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSharedData(@ApplicationContext context: Context): SharedData {
        return SharedData().apply {
            init(context)
        }
    }

    @Singleton
    @Provides
    fun provideItemDao(@ApplicationContext context: Context): ItemDao {
        return AppDatabase.getInstance(context).itemDao()
    }
}