package com.example.module.search.bean

data class Result(
    val hasMore: Boolean,
    val songCount: Long,
    val songs: List<Song>
)