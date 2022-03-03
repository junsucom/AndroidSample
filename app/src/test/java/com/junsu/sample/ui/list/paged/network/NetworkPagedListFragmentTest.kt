package com.junsu.sample.ui.list.paged.network

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import com.junsu.sample.R
import com.junsu.sample.ui.MainActivity
import com.junsu.sample.ui.list.paged.network.api.ItemService
import com.junsu.sample.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(
    application = HiltTestApplication::class,
    instrumentedPackages = ["androidx.loader.content"]
)
@RunWith(RobolectricTestRunner::class)
class NetworkPagedListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var itemService: ItemService

    @BindValue
    lateinit var viewModel: NetworkPagedListViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = NetworkPagedListViewModel(itemService)
    }

    @Test
    fun showSomeResults() = runBlocking {
        launchFragmentInHiltContainer<NetworkPagedListFragment, MainActivity> {
            onView(withId(R.id.view_simpleList_list)).check { view, noViewFoundException ->
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }

                val recyclerView = view as RecyclerView
                assertThat(recyclerView.adapter).isNotNull()
            }
        }
    }
}