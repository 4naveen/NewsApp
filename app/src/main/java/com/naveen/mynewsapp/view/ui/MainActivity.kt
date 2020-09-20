package com.naveen.mynewsapp.view.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naveen.mynewsapp.R
import com.naveen.mynewsapp.service.data.model.MediaItem
import com.naveen.mynewsapp.service.utils.Response
import com.naveen.mynewsapp.view.adapter.MainAdapter
import com.naveen.mynewsapp.viewModel.MainViewModel
import java.util.HashMap


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var data_list: ArrayList<MediaItem> ?= ArrayList()
    var actionbar :ActionBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionbar=supportActionBar
        if (actionbar!=null){
            actionbar!!.setDisplayHomeAsUpEnabled(false)
            actionbar!!.setTitle(R.string.app_name)
        }
        //setSupportActionBar(findViewById(R.id.toolbar))
        setupViewModel()
        setupUI()
        setupObservers()

    }

    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)

    }

    private fun setupUI() {
        recyclerView=findViewById(R.id.recyclerview)
        progressBar=findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this,arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getlist().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Response.Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { results -> retrieveList(results) }
                    }
                    Response.Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Response.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    else -> {

                    }
                }
            }
        })
    }
    private fun retrieveList(resp: java.util.ArrayList<HashMap<String, String>>) {
        actionbar?.setTitle("RepublicNewsApp")
        data_list?.clear()
        for (row in resp){
            val item=MediaItem()
            item.image=row["image_url"]
            item.title=row["title"]
            item.url=row["url"]
            data_list?.add(item)

        }
        data_list?.let { adapter.addList(it) }

    }
}
