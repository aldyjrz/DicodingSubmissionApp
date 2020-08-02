package com.toi.dicodinggithub.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.toi.dicodinggithub.R
import com.toi.dicodinggithub.data.Users
import kotlinx.android.synthetic.main.item_row_followers.view.*
import kotlinx.android.synthetic.main.item_row_following.view.*

class FollowingAdapter (

) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val mData = ArrayList<Users>()

    class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userItems: Users) {
            with(itemView) {
                _rowUsernameFollowing.text = userItems.login

                Glide.with(context)
                    .load(userItems.avatar_url!!)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {

                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    })
                    .into(_rowAvatarFollowing!!)
                _rowUsernameFollowing.setOnClickListener{
                    Toast.makeText(context, _rowUsernameFollowing.text, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun setData(items: ArrayList<Users>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowingViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_following, viewGroup, false)
        return FollowingViewHolder(
            mView
        )
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}