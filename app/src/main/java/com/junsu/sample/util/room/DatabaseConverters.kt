package com.junsu.sample.util.room

import androidx.room.TypeConverter
import com.junsu.sample.model.ItemType
import java.util.*

class DatabaseConverters {
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun datestampToCalendar(value: Long?): Calendar =
            Calendar.getInstance().apply { timeInMillis = value?: Date().time }

    @TypeConverter fun itemTypeToInt(itemType: ItemType): Int = itemType.ordinal

    @TypeConverter fun intToItemType(value: Int): ItemType = ItemType.values().getOrElse(value){ ItemType.TypeA}
}