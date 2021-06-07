package com.example.githubuserlist.userlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserlist.R
import com.example.githubuserlist.databinding.UserListItemBinding
import com.example.githubuserlist.model.User

class UserListAdapter(val context: Context) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    var list : ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]

        holder.binding.userName.text = user.login

        user.avatar_url?.let {
            Glide.with(context)
                .load(it)
                .into(holder.binding.userImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = UserListItemBinding.bind(view)
    }
}
