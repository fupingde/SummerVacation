package com.example.lgs_module.bean

data class Regitser(
    val account: Account,
    val bindings: List<Binding>,
    val code: Int,
    val profile: Profile,
    val token: String
)