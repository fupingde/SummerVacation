package com.example.network.Bean

import java.io.Serializable

data class Songs(
    val code: Int,
    val privileges: List<Privilege2>,
    val songs: List<Song2>
)
data class Song2(
    val a: Any,
    val al: Al,
    val alia: List<String>,
    val ar: List<Ar>,
    val awardTags: Any,
    val cd: String,
    val cf: String,
    val copyright: Int,
    val cp: Int,
    val crbt: Any,
    val djId: Int,
    val dt: Int,
    val entertainmentTags: Any,
    val fee: Int,
    val ftype: Int,
    val h: H,
    val hr: Hr,
    val id: Long,
    val l: L,
    val m: M,
    val mark: Long,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: Any,
    val originCoverType: Int,
    val originSongSimpleData: Any,
    val pop: Int,
    val pst: Int,
    val publishTime: Long,
    val resourceState: Boolean,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Int,
    val rurl: Any,
    val s_id: Int,
    val single: Int,
    val songJumpInfo: Any,
    val sq: Sq,
    val st: Int,
    val t: Int,
    val tagPicList: Any,
    val v: Int,
    val version: Int
): Serializable

data class Al(
    val id: Int,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
): Serializable
data class Ar(
    val alias: List<Any>,
    val id: Int,
    val name: String,
    val tns: List<Any>
): Serializable
data class ChargeInfo2(
    val chargeMessage: Any,
    val chargeType: Int,
    val chargeUrl: Any,
    val rate: Int
): Serializable
data class FreeTrialPrivilege2(
    val cannotListenReason: Int,
    val listenType: Int,
    val playReason: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
): Serializable
data class H(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
): Serializable
data class Hr(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
): Serializable
data class L(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
): Serializable
data class M(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
): Serializable
data class Privilege2(
    val chargeInfoList: List<ChargeInfo2>,
    val code: Int,
    val cp: Int,
    val cs: Boolean,
    val dl: Int,
    val dlLevel: String,
    val downloadMaxBrLevel: String,
    val downloadMaxbr: Int,
    val fee: Int,
    val fl: Int,
    val flLevel: String,
    val flag: Int,
    val freeTrialPrivilege: FreeTrialPrivilege2,
    val id: Long,
    val maxBrLevel: String,
    val maxbr: Int,
    val message: Any,
    val payed: Int,
    val pl: Int,
    val plLevel: String,
    val playMaxBrLevel: String,
    val playMaxbr: Int,
    val preSell: Boolean,
    val rightSource: Int,
    val rscl: Any,
    val sp: Int,
    val st: Int,
    val subp: Int,
    val toast: Boolean
): Serializable
data class Sq(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
): Serializable