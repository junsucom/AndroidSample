package com.junsu.sample.util.room

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.junsu.base.extension.fillColor
import com.junsu.base.extension.px
import com.junsu.base.extension.toByteArray
import com.junsu.sample.Define.DATABASE_NAME
import com.junsu.sample.model.Item
import com.junsu.sample.model.ItemDao
import com.junsu.sample.model.ItemType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Item::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = getInstance(
                                context
                            ).itemDao()
                            for (index in 0..1000) {
                                val now = Date(Date().time - index)
                                val item = Item(
                                    type = ItemType.TypeA,
                                    title = "title $index",
                                    message = "message $index",
                                    date = Calendar.getInstance().also {
                                        it.time = now
                                    },
                                    image = Bitmap.createBitmap(
                                        120.px,
                                        120.px,
                                        Bitmap.Config.ARGB_8888
                                    ).fillColor().toByteArray()
                                )
                                dao.insert(item)
                            }
                        }
                    }
                })
                .build()
        }
    }
}
