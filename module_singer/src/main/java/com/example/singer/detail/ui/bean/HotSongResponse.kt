package com.example.singer.detail.ui.bean

data class HotSongsResponse(
    val artist: ArtistX,
    val hotSongs: List<Song>
)

data class Song(
    val rtUrls: List<String>,
    val ar: List<Artist>,
    val alia: List<String>,
    val name: String,
    val id: Long,
)
data class ArtistX(val picUrl:String,
                  val briefDesc:String

    )
data class Artist(
    val id: Long,
    val name: String,
    val alia: List<String> = emptyList()
)