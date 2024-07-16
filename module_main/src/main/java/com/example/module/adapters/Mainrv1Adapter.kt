package com.example.module.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.Network.Bean.TuijianGedanBean
import com.example.module.main.databinding.Mainrv1itemBinding

class Mainrv1Adapter(private val data: TuijianGedanBean) :
    RecyclerView.Adapter<Mainrv1Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: Mainrv1itemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = Mainrv1itemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data.result[position]

        // 使用 Glide 加载图片并应用圆角变换
        val requestOptions = RequestOptions().transform(RoundedCorners(16)) // 16dp 圆角

        Glide.with(holder.binding.mainvp1picture.context)
            .load(item.picUrl)
            .apply(requestOptions)
            .into(holder.binding.mainvp1picture)

        holder.binding.mainvp1words.text = item.name
    }

    override fun getItemCount(): Int = data.result.size
}
