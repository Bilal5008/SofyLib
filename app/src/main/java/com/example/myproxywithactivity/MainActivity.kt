package com.example.myproxywithactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val movieList: ArrayList<Model> = ArrayList()
    private var mAdapter: ModelsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = ModelsAdapter(movieList)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerview.setLayoutManager(mLayoutManager)
        recyclerview.setItemAnimator(DefaultItemAnimator())
        recyclerview.setAdapter(mAdapter)
        preparedData()
    }

    private fun preparedData() {
        val movie = Model("Mad Max: Fury Road")
        movieList.add(movie)
        movieList.add(movie)
        movieList.add(movie)
        movieList.add(movie)
        movieList.add(movie)
        movieList.add(movie)

        mAdapter?.notifyDataSetChanged()

    }

}