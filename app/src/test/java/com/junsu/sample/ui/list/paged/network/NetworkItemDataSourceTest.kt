package com.junsu.sample.ui.list.paged.network

import android.graphics.Bitmap
import androidx.paging.PagingSource
import com.junsu.base.extension.fillColor
import com.junsu.base.extension.px
import com.junsu.base.extension.toByteArray
import com.junsu.sample.model.Item
import com.junsu.sample.model.ItemType
import com.junsu.sample.ui.list.paged.network.api.ItemService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*
import javax.inject.Inject

@HiltAndroidTest
@Config(
    application = HiltTestApplication::class,
    instrumentedPackages = ["androidx.loader.content"]
)
@RunWith(RobolectricTestRunner::class)
class NetworkItemDataSourceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var itemService: ItemService

    @Before
    fun init() {
        hiltRule.inject()
    }

    private val mocks = listOf(
        Item(
            id = 0,
            type = ItemType.TypeA,
            title = "title 0",
            message = "message 0",
            date = Calendar.getInstance(),
            image = Bitmap.createBitmap(120.px, 120.px, Bitmap.Config.ARGB_8888).fillColor()
                .toByteArray()
        ),
        Item(
            id = 1,
            type = ItemType.TypeA,
            title = "title 1",
            message = "message 1",
            date = Calendar.getInstance(),
            image = Bitmap.createBitmap(120.px, 120.px, Bitmap.Config.ARGB_8888).fillColor()
                .toByteArray()
        )
    )

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runBlocking {
        val pagingSource = NetworkItemDataSource(itemService)
//        assertEquals(
//            expected = PagingSource.LoadResult.Page(
//                data = listOf(mocks[0], mocks[1]),
//                prevKey = null,
//                nextKey = mocks[1].id
//            ),
//            actual = pagingSource.load(
//                PagingSource.LoadParams.Refresh(
//                    key = null,
//                    loadSize = 2,
//                    placeholdersEnabled = false
//                )
//            ),
//        )
    }
}