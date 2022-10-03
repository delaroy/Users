package com.delaroystudios.vpd.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.delaroystudios.domain.model.Users
import com.delaroystudios.vpd.databinding.UsersItemBinding

class UsersListAdapter(private val recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private val TAG: String = "AppDebug"
    var items: List<Users> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        return UsersListViewHolder(
            UsersItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is UsersListViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(itemList: List<Users>){
        items = itemList
        notifyDataSetChanged()
    }

    inner class UsersListViewHolder(private val binding: UsersItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: Users?) {

            binding.let {

                it.root.setOnClickListener {
                    recyclerViewClickListener.clickOnItem(data!!)
                }

                if (data!!.imageUri.isNotEmpty()) {
                    binding.initialsView.visibility = View.GONE
                    binding.profileImage.visibility = View.VISIBLE
                    Glide.with(it.root)
                        .load(data.imageUri)
                        .into(binding.profileImage)
                } else {
                    binding.initialsView.visibility = View.VISIBLE
                    binding.profileImage.visibility = View.GONE
                }

                if (data.name.isNotEmpty()) {
                    binding.name.text = data.name
                    val first: String = data.name.get(0).toString()
                    binding.initials.text = first
                }
                binding.username.text = data.username
                binding.email.text = data.email
            }
        }
    }


    interface RecyclerViewClickListener {
        fun clickOnItem(data: Users)
    }

}