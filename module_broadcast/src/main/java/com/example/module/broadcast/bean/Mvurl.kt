package com.example.module.broadcast.bean

data class MvUrl(
    val code: Int,
    val data: Data
) {
    data class Data(
        val id: Long,
        val url: String,

        )
}
