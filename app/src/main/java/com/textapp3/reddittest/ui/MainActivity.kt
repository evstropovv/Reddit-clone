package com.textapp3.reddittest.ui

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.textapp3.reddittest.R
import com.textapp3.reddittest.ui.epoxy.PagedEpoxyController
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mViewModelProvider: ViewModelProvider.Factory

    @VisibleForTesting
    private lateinit var viewModel: ActivityViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        viewModel = ViewModelProvider(this, mViewModelProvider).get(ActivityViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        val pagingController = PagedEpoxyController()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pagingController.adapter

        viewModel.pagedList.observe(this, Observer {
            pagingController.submitList(it)
            swipeToRefresh.isRefreshing = false
        })

        viewModel.retryLiveData.observe(this, Observer {
            if (it) {
                Snackbar
                    .make(recyclerView, "Error occurred. Try to download data again?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Yes") {
                        viewModel.retry()
                    }.show()
            }
        })
    }
}


