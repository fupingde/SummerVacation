package com.example.module.search.bean.Alubums

data class Song(
    val al: Al,
    val alia: List<String>,
    val ar: List<Ar>,
    val cd: String,
    val djId: Long,
    val id: Long,
    val name: String,
    val videoInfo: VideoInfo
)