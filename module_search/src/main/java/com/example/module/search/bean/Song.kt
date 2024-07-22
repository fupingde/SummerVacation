package com.example.module.search.bean

data class Song(
    val album: Album,
    val alias: List<String>,
    val artists: List<ArtistX>,
    val id: Long,
    val mark: Long,
    val mvid: Long,
    val name: String,
)