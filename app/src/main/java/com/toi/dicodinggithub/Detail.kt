package com.toi.dicodinggithub

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.toi.dicodinggithub.api.ApiMain
import com.toi.dicodinggithub.api.Users
import com.toi.dicodinggithub.model.UserUrl
import org.json.JSONException
import retrofit2.Call


class Detail : AppCompatActivity() {
    var pDialog: AlertDialog? = null
    var photo: String? = null
    var authorName: String? = null
    var date: String? = null
    var title: String? = null
    var content: String? = null
    var link: String? = null
    var tv_login: TextView? = null
    var tv_bio: TextView? = null
    var tv_name: TextView? = null
    var tv_blog: TextView? = null
    var tv_company: TextView? = null
    var tv_location: TextView? = null
    var imgPhoto: ImageView? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user_detail)

        //
        initialRes()
        val usersUrl = intent.getParcelableExtra("extra_url") as UserUrl
        showpDialog()
        getUser(usersUrl.url!!)

    }

    fun initialRes() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        pDialog = builder.create()

        tv_blog = findViewById(R.id.tv_blog)
        tv_name = findViewById(R.id.tv_name)
        tv_bio = findViewById(R.id.tv_bio)
        imgPhoto = findViewById(R.id.avatar_img)
        tv_name = findViewById(R.id.tv_name)
        tv_login = findViewById(R.id.tv_login)
        tv_location = findViewById(R.id.tv_location)
        tv_company = findViewById(R.id.tv_company)

        showpDialog()
    }


    fun getUser(user: String){
        showpDialog()
        ApiMain().services.getUsers(user).enqueue(object :
            retrofit2.Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                response: retrofit2.Response<Users>
            ) {
                //kondisi code jika response sukses
                if(response.code() == 200) {
                    Log.d("response", response.body().toString())
                    hidepDialog()
                    Log.d("response", response.body()!!.name)
                        tv_name!!.text =  response.body()?.name
                        tv_blog!!.text =  response.body()?.blog
                        tv_company!!.text =  response.body()?.company
                        tv_location!!.text = response.body()?.location
                        tv_login!!.text =response.body()?.login
                        val gambar =  response.body()?.avatar_url
                    tv_bio!!.text =  response.body()?.bio

                        Glide.with(applicationContext)
                            .load(gambar)
                            .listener(object : RequestListener<Drawable?> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    hidepDialog()
                                    Toast.makeText(
                                        applicationContext,
                                        "Gambar gagal ditampilkan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    hidepDialog()
                                    return false
                                }
                            })
                            .into(imgPhoto!!)


                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(applicationContext, "Pencarian Gagal Silahkan Refresh", Toast.LENGTH_SHORT).show()
                hidepDialog()
            }
        })
    }

    fun getData(url: String) {
        showpDialog()


        //GET Data dari API menggunakan Library Volley
        val urlJsonArry = url
        val jsonObjRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET,
                urlJsonArry, null,
                Response.Listener { response ->
                    Log.d("Getting", "Get Data User: $response")
                    try {
                        val login = response.getString("login")
                        val id = response.getInt("id")
                        val avatar_url = response.getString("avatar_url")
                        val url = response.getString("url")
                        val followers_url = response.getString("followers_url")
                        val following_url = response.getString("following_url")
                        val company = response.getString("company")

                        val name = response.getString("name")
                        val blog = response.getString("blog")
                        val location = response.getString("location")
                        val bio = response.getString("bio")
                        val public_repos = response.getInt("public_repos")
                        val followers = response.getInt("followers")
                        val following = response.getInt("following")


                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            applicationContext,
                            "Server Error: " + e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    hidepDialog()
                }, Response.ErrorListener { error ->
                    VolleyLog.d("ERROR", error.toString())
                    hidepDialog()
                }) {}
        // Adding request to request queue
        BaseApp.getInstance(applicationContext).addToRequestQueue(jsonObjRequest)
    }
    //method untuk load data yang dipanggil dari adapter
    private fun showpDialog() {
        if (!pDialog!!.isShowing) {
            pDialog!!.show()
        }
    }

    private fun hidepDialog() {
        if (pDialog!!.isShowing) {
            pDialog!!.dismiss()
        }
    }
}