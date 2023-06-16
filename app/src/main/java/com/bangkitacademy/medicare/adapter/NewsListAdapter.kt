package com.bangkitacademy.medicare.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkitacademy.medicare.data.remote.response.ArticlesItem
import com.bangkitacademy.medicare.databinding.ItemBerandaBinding
import com.bangkitacademy.medicare.utils.dateFromPublicApiToUser

class NewsListAdapter :
    PagingDataAdapter<ArticlesItem, NewsListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemBerandaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ArticlesItem) {
            binding.apply {
                tvTglBerita.text = data.publishedAt.dateFromPublicApiToUser()
                tvJudulBerita.text = data.title

                root.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
                    startActivity(root.context, intent, null)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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