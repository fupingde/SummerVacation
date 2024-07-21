package com.example.module.broadcast.bean

data class MvData(
    val loadingPic: String,
    val subed: Boolean,
    val mp: Mp,
    val data: Mdata,
    val code: Int
)

data class Mp(
    val id: Long,
    val fee: Int,
    val mvFee: Int,
    val payed: Int,
    val pl: Int,
    val dl: Int,
    val cp: Int,
    val sid: Int,
    val st: Int,
)

data class Mdata(
    val id: Long,
    val name: String,
    val artistId: Long,
    val artistName: String,
    val coverId_str: String,
    val coverId: Long,

    val playCount: Long,
    val subCount: Long,
    val shareCount: Long,
    val commentCount: Long,

    val publishTime: String,
    val artists: List<Artist>,
    val commentThreadId: String,
    val videoGroup: List<VideoGroup>
)



data class Artist(
    val id: Long,
    val name: String,
    val img1v1Url: String,
    val followed: Boolean
)

data class VideoGroup(
    val id: Long,
    val name: String,
    val type: Int
)
