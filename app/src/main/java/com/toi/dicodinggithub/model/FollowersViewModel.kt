package com.toi.dicodinggithub.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.toi.dicodinggithub.data.Users
import com.toi.dicodinggithub.ui.MainActivity
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowersViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setUser(queryUser: String) {

        try {
            val client = AsyncHttpClient()
            val listItems = ArrayList<Users>()

            val url = "https://api.github.com/users/$queryUser/followers"

            client.addHeader("Authorization", "token b3b19438e86e9cb535196361874f8153ca90fdd1")
            client.addHeader("User-Agent", "request")

            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<cz.msebera.android.httpclient.Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)

                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val user = Users()
                        user.login = item.getString("login")
                        user.avatar_url = item.getString("avatar_url")

                        listItems.add(user)
                    }
                    listUsers.postValue(listItems)

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    Log.d("onFailure", error.message.toString())
                }
            })
        } catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }

    }

    fun getUser(): LiveData<ArrayList<Users>> {
        return listUsers
    }

}
