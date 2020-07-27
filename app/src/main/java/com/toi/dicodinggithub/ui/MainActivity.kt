package com.toi.dicodinggithub.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.toi.dicodinggithub.R
import com.toi.dicodinggithub.adapter.UsersAdapter
import com.toi.dicodinggithub.model.MainViewModel

import com.toi.dicodinggithub.api.ApiMain
import com.toi.dicodinggithub.api.SearchResponse
import com.toi.dicodinggithub.data.Users
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: AlertDialog


    private var list = ArrayList<Users>()
    private lateinit var mainViewModel : MainViewModel
    fun initDialog(){
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading)
        dialog = builder.create()

        recycleview.setHasFixedSize(true)
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDialog()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        findUser()
     }



    fun showRecyclerCardView(list: ArrayList<Users>) {
        recycleview.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = UsersAdapter(list)
        recycleview!!.adapter = cardViewHeroAdapter
    }

    private fun findUser(){

        username_search.setInputType(InputType.TYPE_CLASS_TEXT);
        username_search.setImeActionLabel("SEARCH", KeyEvent.KEYCODE_ENTER);
        username_search.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == KeyEvent.KEYCODE_ENTER) {
                Log.d("search", username_search.text.toString())

                mainViewModel.getUser(applicationContext, username_search.text.toString())
                handled = true
            }
            handled
        }

        username_search.setOnTouchListener(object : View.OnTouchListener{

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                // val DRAWABLE_LEFT = 0
                // val DRAWABLE_TOP = 1
                Log.d("search", username_search.text.toString())

                val DRAWABLE_RIGHT = 2
                //  val DRAWABLE_BOTTOM = 3
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= username_search.getRight() - username_search.getCompoundDrawables().get(DRAWABLE_RIGHT).getBounds().width()) {
                        mainViewModel.getUser(applicationContext, username_search.text.toString())
                        return true
                    }
                }
                return false
            }

        })
    }
}

