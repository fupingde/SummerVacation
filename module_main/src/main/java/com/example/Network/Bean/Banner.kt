package com.example.Network.Bean

data class Banner(
    val code: Int,
    val `data`: List<Data>
)
data class Data(
    val exclusive: Boolean,
    val pic: String,
    val targetId: Long,
    val targetType: Int,
    val typeTitle: String,
    val url: String
)