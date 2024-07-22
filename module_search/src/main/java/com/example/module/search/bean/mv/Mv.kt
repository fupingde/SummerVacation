package com.example.module.search.bean.mv

data class Mv(
    val alias: List<String>?,
    val artistId: Long,
    val artistName: String,
    val artists: List<Artist>,
    val cover: String,
    val id: Long,
    val name: String,
    val playCount: Long,
)