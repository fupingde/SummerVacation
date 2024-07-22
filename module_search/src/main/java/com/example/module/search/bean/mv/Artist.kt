package com.example.module.search.bean.mv

data class Artist(
    val alias: List<String>?,
    val id: Int,
    val name: String,
    val transNames: List<String>?
)