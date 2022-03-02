package com.junsu.sample.ui.list.paged.network

import android.os.Build
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.junsu.sample.R
import com.junsu.sample.ui.MainActivity
import com.junsu.sample.ui.list.paged.network.api.ItemService
import com.junsu.sample.util.launchFragmentInHiltContainer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.Thread.sleep
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
            //https://developer.android.com/guide/fragments/test
            onView(withId(R.id.view_simpleList_list)).check { view, noViewFoundException ->
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }
                sleep(2000)

                val recyclerView = view as RecyclerView
                assertThat(recyclerView.adapter).isNotNull()
                assertThat(recyclerView.adapter!!.itemCount).isGreaterThan(0)
            }
        }
    }
}