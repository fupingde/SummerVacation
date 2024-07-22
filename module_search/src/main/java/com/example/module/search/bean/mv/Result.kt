package com.example.module.search.bean.mv

data class Result(
    val hlWords: Any?,
    val mvCount: Int,
    val mvs: List<Mv>
)