package com.bangkitacademy.medicare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkitacademy.medicare.data.remote.response.ArticlesItem
import com.bangkitacademy.medicare.databinding.ItemBerandaBinding
import com.bumptech.glide.Glide

class NewsListAdapter :
    PagingDataAdapter<ArticlesItem, NewsListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemBerandaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ArticlesItem) {
            binding.apply {
                Glide.with(root.context)
                    .load(data.urlToImage)
                    .into(imgBerita)
                tvTglBerita.text = data.publishedAt
                tvJudulBerita.text = data.title

//                root.setOnClickListener { }
            }
        }
    }

    override fun onBindViewHolder(holder: NewsListAdapter.MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemBerandaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }
}