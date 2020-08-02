package com.toi.dicodinggithub.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.toi.dicodinggithub.R
import com.toi.dicodinggithub.adapter.UsersAdapter
import com.toi.dicodinggithub.api.ApiMain
import com.toi.dicodinggithub.api.SearchResponse
import com.toi.dicodinggithub.data.Users
import com.toi.dicodinggithub.model.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: AlertDialog

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
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDialog()


        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)


        findUser()
     }



    fun showRecyclerCardView(list: ArrayList<Users>) {
        recycleview.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = UsersAdapter(list)
        recycleview!!.adapter = cardViewHeroAdapter
        dialog.dismiss()
    }

    fun getUser(context: Context, user: String){
        dialog.show()

        ApiMain().services.searchUser(user).enqueue(object :
            retrofit2.Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: retrofit2.Response<SearchResponse>
            ) {
                //Tulis code jika response sukses
                showRecyclerCardView(response.body()!!.users)
                dialog.dismiss()

                if(response.code() == 200) {
                    response.body()?.users?.let {
                        showRecyclerCardView(it) //it return List<Team>
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(context, "Pencarian Gagal Silahkan Refresh", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun findUser(){

        username_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        username_search.setImeActionLabel("SEARCH", KeyEvent.KEYCODE_ENTER);
        username_search.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("search", username_search.text.toString())
                getUser(applicationContext, username_search.text.toString())
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
                  getUser(applicationContext, username_search.text.toString())
                        return true
                    }
                }
                return false
            }

        })
    }
}

