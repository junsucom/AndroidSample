package com.junsu.sample.ui.list.paged.network

import android.os.Build
import androidx.annotation.UiThread
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
import com.junsu.sample.ui.list.paged.network.api.ItemService
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(
    application = HiltTestApplication::class
)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(ItemService::class)
class NetworkPagedListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    @UiThread
    fun showSomeResults() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()).apply {
            setGraph(R.navigation.nav_graph)
            setCurrentDestination(R.id.NetworkPagedListFragment)
        }

        val networkPagedListFragment = launchFragmentInContainer<NetworkPagedListFragment>()

        networkPagedListFragment.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.view_simpleList_list)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertThat(recyclerView.adapter).isNotNull()
            assertThat(recyclerView.adapter!!.itemCount).isGreaterThan(0)
        }
    }
}
