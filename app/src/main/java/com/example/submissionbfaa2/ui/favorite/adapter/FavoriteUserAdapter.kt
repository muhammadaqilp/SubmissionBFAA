package com.example.submissionbfaa2.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionbfaa2.R
import com.example.submissionbfaa2.database.FavoriteUser
import com.example.submissionbfaa2.databinding.ItemListUserBinding
import com.example.submissionbfaa2.helper.FavDiffCallback

class FavoriteUserAdapter(
    private val listener: OnFavoriteClickCallback? = null
) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private val listFavorite = ArrayList<FavoriteUser>()
    fun setListFavorite(listFavorite: List<FavoriteUser>) {
        val diffCallback = FavDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class ViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteUser) {
            with(binding) {
                tvId.text = favorite.userId
                tvUsername.text = favorite.username
                Glide.with(itemView)
                    .load(favorite.avatar)
                    .apply(
                        RequestOptions().placeholder(R.drawable.noimg)
                    )
                    .into(ivImage)
                itemView.setOnClickListener {
                    listener?.onFavoriteClicked(favorite.username)
                }
            }
        }
    }

    interface OnFavoriteClickCallback {
        fun onFavoriteClicked(username: String?)
    }
}