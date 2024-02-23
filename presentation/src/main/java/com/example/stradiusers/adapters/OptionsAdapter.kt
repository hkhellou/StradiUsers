package com.example.stradiusers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stradiusers.databinding.OptionsListViewBinding
import com.example.stradiusers.utils.OptionsList

class OptionsAdapter(
    private var optionsList: List<OptionsList>
) :
    RecyclerView.Adapter<OptionsAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: OptionsListViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            OptionsListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = optionsList[position].image
        val title = optionsList[position].title
        val body = optionsList[position].body
        holder.binding.imgUser.setImageDrawable(image)
        holder.binding.tvTitle.text = title
        holder.binding.tvBody.text = body

    }
}