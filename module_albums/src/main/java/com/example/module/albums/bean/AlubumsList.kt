package com.example.module.albums.bean

data class Album ( val picUrl:String,
              val description :String,
              val name:String)

data class AlubumsList(
    val resourceState: Boolean,
    val songs: List<Song>,
    val album:Album

)