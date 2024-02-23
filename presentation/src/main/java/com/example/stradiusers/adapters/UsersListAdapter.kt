package com.example.stradiusers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.operations.UserParams
import com.example.stradiusers.databinding.UsersListAdapterViewBinding
import com.squareup.picasso.Picasso
import com.example.stradiusers.utils.CircleImageTrasnformator

class UsersListAdapter(
    private var usersList: List<UserParams>,
    private val userClickListener: UserClickListener
) :
    RecyclerView.Adapter<UsersListAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: UsersListAdapterViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UsersListAdapterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val name = usersList[position].name.first
        val surname = usersList[position].name.last
        val email = usersList[position].email
        val image = usersList[position].picture.large
        holder.binding.tvUserFullname.text = "$name $surname"
        holder.binding.tvUserEmail.text = email
        Picasso.get().load(image).transform(CircleImageTrasnformator()).into(holder.binding.imgUser)
        holder.itemView.setOnClickListener {
            userClickListener.userClickListener(usersList[position])
        }
    }

    interface UserClickListener {
        fun userClickListener(user: UserParams)
    }
}