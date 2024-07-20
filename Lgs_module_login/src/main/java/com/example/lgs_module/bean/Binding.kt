package com.example.lgs_module.bean

data class Binding(
    val bindingTime: Long,
    val expired: Boolean,
    val expiresIn: Long,
    val id: Long,
    val refreshTime: Long,
    val tokenJsonStr: String,
    val type: Long,
    val url: String,
    val userId: Long
)