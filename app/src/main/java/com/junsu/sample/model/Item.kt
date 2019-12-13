package com.junsu.sample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.junsu.sample.Define
import com.junsu.sample.Define.DATE_FORMAT
import java.util.*

@Entity(tableName = Define.TABLE_ITEM)
data class Item(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "date") val date: Calendar = Calendar.getInstance(),
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image: ByteArray? = null
) {
    val dateString: String
        get() = DATE_FORMAT.format(date.time)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}