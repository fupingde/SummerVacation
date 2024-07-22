package com.example.module.search.bean

import com.example.module.search.bean.singer.Artist

data class DataAlbum(
    val result: Result1,
    val code: Int
)

data class Result1(
    val albums: List<Album1>,
    val albumCount: Int
)

data class Album1(
    val name: String,
    val id: Long,
    val picId: Long,
    val blurPicUrl: String,
    val companyId: Int,
    val pic: Long,
    val picUrl: String,
    val publishTime: Long,
    val description: String?,
    val tags: String?,
    val artist: com.example.module.search.bean.Artist,
    val songs: List<String>?,
    )

data class Artist(
    val name: String,
    val id: Long,
    val picId: Long,
    val img1v1Id: Long,
    val briefDesc: String?,
    val picUrl: String,
    val img1v1Url: String,
    val albumSize: Int,
    val alias: List<String>,
    val trans: String?,
    val musicSize: Int,
    val topicPerson: Int,
    val picId_str: String?,
    val img1v1Id_str: String?,
    val alia: List<String>
)
