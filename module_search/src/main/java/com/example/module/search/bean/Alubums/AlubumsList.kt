package com.example.module.search.bean.Alubums

data class AlubumsList(
    val resourceState: Boolean,
    val songs: List<Song>,
    val picUrl:String,
    val description :String
)