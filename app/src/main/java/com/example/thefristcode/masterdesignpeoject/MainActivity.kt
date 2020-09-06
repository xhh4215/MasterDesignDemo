package com.example.thefristcode.masterdesignpeoject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val fruits = mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango)
    )

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            //设置导航图标显示出来
            it.setDisplayHomeAsUpEnabled(true)
            //设置导航图标的图片样式
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        //设置Call为菜单的首选项
        navView.setCheckedItem(R.id.navCall)
        //关闭菜单的操作
        navView.setNavigationItemSelectedListener {
            drawerlayout.closeDrawers()
            true
        }
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Data delete", Snackbar.LENGTH_SHORT).setAction("Undo") {
                Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
            }
                .show()
        }

        initFruit()
        val layoutmanager = GridLayoutManager(this, 2)
        recyclerview.layoutManager = layoutmanager
        val adapter = FruitAdapter(this, fruitList)
        recyclerview.adapter = adapter

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            refreashFruit(adapter)
        }
    }

    private fun refreashFruit(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruit()
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_one -> {
                Toast.makeText(this, "one", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_two -> {
                Toast.makeText(this, "two", Toast.LENGTH_SHORT).show()

            }
            R.id.menu_three -> {
                Toast.makeText(this, "three", Toast.LENGTH_SHORT).show()

            }
            android.R.id.home -> {
                drawerlayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }


    private fun initFruit() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }
}