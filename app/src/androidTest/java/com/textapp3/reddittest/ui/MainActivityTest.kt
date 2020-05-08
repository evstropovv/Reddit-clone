package com.textapp3.reddittest.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.textapp3.reddittest.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private var mActivity: MainActivity? = null

    lateinit var mRecyclerView: RecyclerView

    private var itemCount = 0

    @get:Rule
    public val mActivityRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {}

    @Before
    fun setUpTest() {
        this.mActivity = this.mActivityRule.activity
        this.mRecyclerView = this.mActivity?.findViewById<RecyclerView>(R.id.recyclerView)!!
    }

    @Test
    fun recyclerViewShowItems() {
        Thread.sleep(2000)

        this.itemCount = this.mRecyclerView.adapter?.itemCount!!

        if (this.itemCount > 0) {
            for (i in 0 until (mRecyclerView.layoutManager!! as LinearLayoutManager).findLastVisibleItemPosition()) {
                assertThat(mRecyclerView.layoutManager?.findViewByPosition(i), isDisplayed())
            }
        }
    }

    @Test
    fun newItemsAddedAfterScrolling() {
        Thread.sleep(2000)
        itemCount = mRecyclerView.adapter?.itemCount!!
        onView(withId(R.id.recyclerView)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1)
        )
        Thread.sleep(2000)
        val newItemCount = this.mRecyclerView.adapter?.itemCount!!
        assert(newItemCount > itemCount)
    }

}