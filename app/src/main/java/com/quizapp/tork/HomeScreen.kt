package com.quizapp.tork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quizapp.tork.adapter.CategoryAdapter
import com.quizapp.tork.model.Category
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        setSupportActionBar(toolbar)

        val cat_recycler_view =  findViewById<RecyclerView>(R.id.cat_items)
        val data = ArrayList<Category>()

            data.add(Category( 1,"Maths",R.drawable.wheel))
            data.add(Category( 2,"Science",R.drawable.wheel))
            data.add(Category( 3,"Physics",R.drawable.wheel))
            data.add(Category( 4,"English",R.drawable.wheel))

        val adapter = CategoryAdapter(data)
        cat_recycler_view.layoutManager = GridLayoutManager(this,2)
        cat_recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.wallet){

            Toast.makeText(this,"wallet clicked",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}