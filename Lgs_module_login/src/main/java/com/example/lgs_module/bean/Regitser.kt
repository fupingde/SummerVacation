package com.example.lgs_module.bean

data class Regitser(
    val account: Account,
    val bindings: List<Binding>,
    val code: Long,
    val profile: Profile,
    val token: String
)