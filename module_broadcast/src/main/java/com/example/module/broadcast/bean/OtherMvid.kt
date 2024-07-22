package com.example.module.broadcast.bean

// OtherMvid.kt
data class OtherMvid(
    val mvs: List<Mv>,
    val code: Int
)

data class Mv(
    val id: Long,
    val cover: String,
    val name: String,
    val playCount: Int,
    val briefDesc: String?,
    val desc: String?,
    val artistName: String,
    val artistId: Int,
    val duration: Int,
    val mark: Int,
    val artists: List<Artist>,
    val alg: String
)

data class mArtist(
    val id: Int,
    val name: String,
    val transNames: List<String>?
)
