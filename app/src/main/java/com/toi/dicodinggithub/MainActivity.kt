package com.toi.dicodinggithub

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.toi.dicodinggithub.adapter.UsersAdapter
import com.toi.dicodinggithub.api.ApiMain
import com.toi.dicodinggithub.api.SearchResponse
import com.toi.dicodinggithub.model.Users
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: AlertDialog

    lateinit var recyclerView: RecyclerView
    lateinit var mswipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading)
        dialog = builder.create()
        mswipeRefreshLayout = findViewById(R.id.swipeRefresh)
        recyclerView = findViewById(R.id.recycleview)
        recyclerView.setHasFixedSize(true)

        mswipeRefreshLayout.setOnRefreshListener {
            mswipeRefreshLayout.isRefreshing = false
        }
        findText()
     }
    fun getUser(user: String){
        showpDialog()
        ApiMain().services.searchUser(user).enqueue(object :
            retrofit2.Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: retrofit2.Response<SearchResponse>
            ) {
                //Tulis code jika response sukses
                if(response.code() == 200) {
                    hidepDialog()

                    Log.d("users", response.body()?.users.toString())

                    response.body()?.users?.let {
                        showRecyclerCardView(it) //it return List<Team>
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Pencarian Gagal Silahkan Refresh", Toast.LENGTH_SHORT).show()
                hidepDialog()
            }
        })
    }

    private fun showpDialog() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }


    private fun hidepDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    fun showRecyclerCardView(list: ArrayList<Users>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = UsersAdapter(list)
        recyclerView!!.adapter = cardViewHeroAdapter
    }

    private fun findText(){

          username_search.setInputType(InputType.TYPE_CLASS_TEXT);
        username_search.setImeActionLabel("SEARCH", KeyEvent.KEYCODE_ENTER);
        username_search.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == KeyEvent.KEYCODE_ENTER) {
                Log.d("search", username_search.text.toString())

                getUser(username_search.text.toString())
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
                        getUser(username_search.text.toString())
                        return true
                    }
                }
                return false
            }

        })
    }
}

