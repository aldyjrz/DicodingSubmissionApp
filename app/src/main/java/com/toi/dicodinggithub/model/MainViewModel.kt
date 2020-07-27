package com.toi.dicodinggithub.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toi.dicodinggithub.api.ApiMain
import com.toi.dicodinggithub.api.SearchResponse
import retrofit2.Call


class MainViewModel : ViewModel() {
    val liveDataUser = MutableLiveData<ArrayList<SearchResponse>>()
    val list = ArrayList<SearchResponse>()

    fun getUserData(): MutableLiveData<ArrayList<SearchResponse>> {
        return liveDataUser
    }


    fun getUser(comtext: Context, user: String){
         ApiMain().services.searchUser(user).enqueue(object :
            retrofit2.Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: retrofit2.Response<SearchResponse>
            ) {
                //Tulis code jika response sukses
                if(response.code() == 200) {
                    response.body()?.users?.let {

                        list.add(SearchResponse(it))
                        liveDataUser.postValue(list)
                     }
                 }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(comtext, "Pencarian Gagal Silahkan Refresh", Toast.LENGTH_SHORT).show()
             }

         })
    }

}