package com.example.lgs_module.bean

data class Account(
    val anonimousUser: Boolean,
    val ban: Long,
    val baoyueVersion: Long,
    val createTime: Long,
    val donateVersion: Int,
    val id: Long,
    val salt: String,
    val status: Int,
    val tokenVersion: Long,
    val type: Int,
    val uninitialized: Boolean,
    val userName: String,
    val vipType: Long,
    val viptypeVersion: Long,
    val whitelistAuthority: Long
)