package com.toi.dicodinggithub.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.toi.dicodinggithub.adapter.UsersAdapter
import com.toi.dicodinggithub.api.ApiMain
import com.toi.dicodinggithub.api.SearchResponse
import com.toi.dicodinggithub.data.Users
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call

class MainViewModel  : ViewModel(){

    fun getList(list: ArrayList<Users>): ArrayList<Users> {
        return list
    }

}