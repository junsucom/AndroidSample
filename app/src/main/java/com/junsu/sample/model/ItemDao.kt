package com.junsu.sample.model

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.junsu.sample.Define


@Dao
interface ItemDao {
    @Query("SELECT * from ${Define.TABLE_ITEM} ORDER BY date DESC")
    fun getByDate(): DataSource.Factory<Int, Item>

    @Insert
    suspend fun insert(item: Item) : Long

    @Query("DELETE FROM ${Define.TABLE_ITEM}")
    suspend fun deleteAll()

    @Query("DELETE FROM ${Define.TABLE_ITEM} WHERE id = :id")
    fun deleteItemId(id: Long)
}