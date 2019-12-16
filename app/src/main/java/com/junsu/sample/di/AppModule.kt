package com.junsu.sample.di

import com.google.gson.GsonBuilder
import com.junsu.sample.Define
import com.junsu.sample.sector.home.HomeViewModel
import com.junsu.sample.sector.list.paged.network.NetworkPagedListViewModel
import com.junsu.sample.sector.list.paged.network.api.ItemService
import com.junsu.sample.sector.list.paged.room.RoomPagedListViewModel
import com.junsu.sample.util.apiInterceptor
import com.junsu.sample.util.room.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { AppDatabase.getInstance(androidContext()).itemDao() }
    single {
        Retrofit.Builder()
            .baseUrl(Define.URL_HOST)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .apply {
                client(apiInterceptor)
            }
            .build().create(ItemService::class.java)
    }
    viewModel { HomeViewModel() }
    viewModel { RoomPagedListViewModel(get()) }
    viewModel { NetworkPagedListViewModel(get()) }
}
