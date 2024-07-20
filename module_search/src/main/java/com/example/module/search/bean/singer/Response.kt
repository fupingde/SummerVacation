package com.example.module.search.bean.singer

data class ResponseData(
    val msg: String,
    val code: Int,
    val data: ArtistData
)

data class ArtistData(
    val totalCount: Int,
    val list: List<Artist>
)

data class Artist(
    val artistId: Long,
    val artistName: String,
    val artistAvatarPicUrl: String
)
