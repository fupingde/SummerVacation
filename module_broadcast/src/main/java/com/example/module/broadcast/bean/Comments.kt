package com.example.module.broadcast.bean

data class Comments(
    val code: Int,
    val comments: List<Onecomment>,
    val total: Long


)

data class Onecomment(
    val user: User,
    val content: String,
    val timeStr: String,
    val likedCount: Long
)

data class User(
    val avatarUrl: String,
    val nickname: String,
    val userId: Long,


    )
