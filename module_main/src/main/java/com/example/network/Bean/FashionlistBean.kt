package com.example.network.Bean

data class FashionlistBean(
    val code: Int,
    val `data`: Data
)
data class Data(
    val page: Page,
    val playlist: List<Playlist>
)
data class Page(
    val cursor: Int,
    val more: Boolean,
    val size: Int,
    val total: Int
)
data class Playlist(
    val cover: String,
    val id: Long,
    val name: String,
    val playCount: Int,
    val songCount: Int,
    val userId: Long,
    val userName: String
)