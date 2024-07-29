package com.example.module.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.network.Bean.FashionlistBean
import com.example.module.main.databinding.ItemMainrvBinding

class MainFashionAdapter(private val data: FashionlistBean, private val onItemClick: (Long, String, String) -> Unit) :
    RecyclerView.Adapter<MainFashionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMainrvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data.data.playlist[position]

        // 使用 Glide 加载图片并应用圆角变换
        val roundRadius = RequestOptions().transform(RoundedCorners(16)) // 16dp 圆角

        Glide.with(holder.binding.mainrvpicture.context)
            .load(item.cover)
            .apply(roundRadius)
            .into(holder.binding.mainrvpicture)

        holder.binding.mainvp1words.text = item.name

        // 设置点击事件
        holder.itemView.setOnClickListener {
            onItemClick(item.id, item.name, item.cover)
        }
    }

    override fun getItemCount(): Int = data.data.playlist.size
}
