package com.example.network.Bean

data class songsurl(
    val code: Int,
    val `data`: List<Data2>
)
data class Data2(
    val br: Int,
    val canExtend: Boolean,
    val channelLayout: Any,
    val code: Int,
    val effectTypes: Any,
    val encodeType: String,
    val expi: Int,
    val fee: Int,
    val flag: Int,
    val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
    val freeTrialInfo: Any,
    val freeTrialPrivilege: FreeTrialPrivilege3,
    val gain: Double,
    val id: Long,
    val level: String,
    val levelConfuse: Any,
    val md5: String,
    val message: Any,
    val payed: Int,
    val peak: Double,
    val podcastCtrp: Any,
    val rightSource: Int,
    val size: Int,
    val time: Int,
    val type: String,
    val uf: Any,
    val url: String,
    val urlSource: Int
)
data class FreeTimeTrialPrivilege(
    val remainTime: Int,
    val resConsumable: Boolean,
    val type: Int,
    val userConsumable: Boolean
)
data class FreeTrialPrivilege3(
    val cannotListenReason: Any,
    val listenType: Any,
    val playReason: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)