package com.example.module.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.Network.Bean.BannerX
import com.example.module.main.databinding.ItemBannerBinding

class BannerAdapter(private val bannerData: List<BannerX>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        val roundRadius = RequestOptions().transform(RoundedCorners(60)) // 圆角

        fun bind(banner: BannerX) {
            Glide.with(binding.imageView.context)
                .load(banner.pic)
                .apply(roundRadius)
                .into(binding.imageView)

            // 设置点击事件，打开URL
                binding.root.setOnClickListener {
                    if (banner.url.startsWith("http")) {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(banner.url)
                    }
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerData[position])
    }

    override fun getItemCount(): Int {
        return bannerData.size
    }
}
