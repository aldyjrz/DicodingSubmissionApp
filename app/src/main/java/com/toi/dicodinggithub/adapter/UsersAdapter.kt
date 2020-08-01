package com.toi.dicodinggithub.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.toi.dicodinggithub.R
import com.toi.dicodinggithub.data.Users
import com.toi.dicodinggithub.ui.DetailUserActivity
import kotlinx.android.synthetic.main.item_users.view.*


class UsersAdapter(private val dataUsers: ArrayList<Users>) : RecyclerView.Adapter<UsersAdapter.CardViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return CardViewViewHolder(view)
    }





    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewViewHolder, pos: Int) {
        val user = dataUsers[pos]

        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(150, 220))
            .into(holder.imgPhoto)

        holder.tvUserName.text = user.login.toString()

        holder.cardView.setOnClickListener {
            val i = Intent(holder.itemView.context, DetailUserActivity::class.java)
            Toast.makeText(holder.itemView.context, user.login.toString(), Toast.LENGTH_SHORT).show()
            val url = user.login
            val person = Users(url = url
           )

            i.putExtra("url", person)
            Log.d("jancok", person.toString())
            holder.itemView.context.startActivity(i)
         }
    }

    override fun getItemCount(): Int {
        return dataUsers.size
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //inisialisasi resource dengan id masing2
        var imgPhoto: ImageView = itemView.avatar_search
        var tvUserName: TextView = itemView.username_search
        var cardView: CardView = itemView.cardView
    }
}