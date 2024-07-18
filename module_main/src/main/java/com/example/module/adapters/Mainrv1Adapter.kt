package com.example.module.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.Network.Bean.TuijianGedanBean
import com.example.module.main.databinding.MainrvitemBinding

class Mainrv1Adapter(private val data: TuijianGedanBean, private val onItemClick: (Long, String, String) -> Unit) :
    RecyclerView.Adapter<Mainrv1Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: MainrvitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainrvitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data.result[position]

        // 使用 Glide 加载图片并应用圆角变换
        val roundRadius = RequestOptions().transform(RoundedCorners(16)) // 16dp 圆角

        Glide.with(holder.binding.mainrvpicture.context)
            .load(item.picUrl)
            .apply(roundRadius)
            .into(holder.binding.mainrvpicture)

        holder.binding.mainvp1words.text = item.name

        // 设置点击事件
        holder.itemView.setOnClickListener {
            onItemClick(item.id.toLong(), item.name, item.picUrl)
        }
    }

    override fun getItemCount(): Int = data.result.size
}
