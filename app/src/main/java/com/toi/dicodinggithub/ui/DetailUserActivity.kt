package com.toi.dicodinggithub.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.toi.dicodinggithub.R
import com.toi.dicodinggithub.api.ApiMain
import com.toi.dicodinggithub.data.Users
import kotlinx.android.synthetic.main.layout_user_detail.*
import retrofit2.Call
import retrofit2.Response


class DetailUserActivity : AppCompatActivity() {
    var pDialog: AlertDialog? = null


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user_detail)


        //show loading / progress dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loading()
        }
         val usersUrl = intent.getParcelableExtra("url") as Users
        //memanggil fungsi untuk menampilkan detail profile dari github
        showpDialog()
        Log.d("jancok_url", usersUrl.toString())
        getUser(usersUrl.url!!)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun loading() {
        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.layout_loading)

        builder.setCancelable(false)
        pDialog = builder.create()

        showpDialog()
    }

   fun updateUi(response: Response<Users>){
       tv_login_name!!.text =  "${response.body()?.name} / ${response.body()?.login}"
       tv_blog!!.text =  response.body()?.blog
       tv_company!!.text =  response.body()?.company
       tv_location!!.text = response.body()?.location
       tv_bio!!.text =  response.body()?.bio
       tv_follower!!.text = "Followers ${response.body()?.followers}"
       tv_following!!.text = "Following ${response.body()?.following}"
       tv_follower.setOnClickListener{
           Toast.makeText(applicationContext, response.body()?.followers.toString()+ "\n Followers", Toast.LENGTH_SHORT ).show()
       }
       tv_following.setOnClickListener{
           Toast.makeText(applicationContext, response.body()?.following.toString()+ "\n Following ", Toast.LENGTH_SHORT ).show()
       }

       Glide.with(applicationContext)
           .load(response.body()?.avatar_url)
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
           .into(avatar_img!!)
   }


    fun getUser(user: String){
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

                    //updating ui
                    updateUi(response)


                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(applicationContext, "Pencarian Gagal Silahkan Refresh", Toast.LENGTH_SHORT).show()
                hidepDialog()
            }
        })
    }

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