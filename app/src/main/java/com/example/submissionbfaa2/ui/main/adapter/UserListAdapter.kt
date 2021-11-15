package com.example.submissionbfaa2.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionbfaa2.R
import com.example.submissionbfaa2.data.response.UserItem
import com.example.submissionbfaa2.databinding.ItemListUserBinding

class UserListAdapter(
    private val context: Context,
    val listUser: MutableList<UserItem> = mutableListOf(),
    val listener: OnUserClickCallback? = null
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemListUserBinding.bind(itemView)

        fun bind(dataUser: UserItem) {
            with(itemView) {
                binding.tvId.text = dataUser.id.toString()
                binding.tvUsername.text = dataUser.login
                Glide.with(this)
                    .load(dataUser.avatarUrl)
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.noimg)
                    )
                    .into(binding.ivImage)

                setOnClickListener {
                    listener?.onUserClicked(dataUser)
                }
            }
        }
    }

    interface OnUserClickCallback {
        fun onUserClicked(data: UserItem)
    }
}