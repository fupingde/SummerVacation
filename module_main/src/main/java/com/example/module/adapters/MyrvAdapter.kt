package com.example.module.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.Network.Bean.MyrvBean
import com.example.module.main.databinding.ItemMyrvBinding

class MyrvAdapter(
    private val data: List<MyrvBean>,
    private val onItemClick: (Long, String, String) -> Unit
) : RecyclerView.Adapter<MyrvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMyrvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        // 使用 Glide 加载图片并应用圆角变换
        val roundRadius = RequestOptions().transform(RoundedCorners(16)) // 16dp 圆角

        Glide.with(holder.binding.myrvimage.context)
            .load(item.playlistImageUrl)
            .apply(roundRadius)
            .into(holder.binding.myrvimage)

        holder.binding.myrvwords.text = item.playlistName

        // 设置点击事件
        holder.itemView.setOnClickListener {
            onItemClick(item.playlistId, item.playlistName, item.playlistImageUrl)
        }
    }

    override fun getItemCount(): Int = data.size
}
