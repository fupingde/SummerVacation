package com.example.Network.Bean

data class ListsData(
    val code: Int,
    val list: List<Playlistr>
)

data class Playlistr(
    val updateFrequency: String,
    val backgroundCoverId: Long,
    val tsSongCount: Int,
    val updateTime: Long,
    val playCount: Long,
    val trackCount: Int,
    val commentThreadId: String,
    val cloudTrackCount: Int,
    val description: String,
    val userId: Long,
    val name: String,
    val id: Long,
    val coverImgUrl: String,
)
