package com.example.network.Bean

data class Banner(
    val banners: List<BannerX>,
    val code: Int,
    val trp: Trp
)
data class BannerX(
    val adDispatchJson: String,
    val adLocation: String,
    val adSource: String,
    val adid: String,
    val adurlV2: String,
    val alg: String,
    val bannerBizType: String,
    val bannerId: String,
    val encodeId: String,
    val exclusive: Boolean,
    val extMonitor: List<ExtMonitor>,
    val extMonitorInfo: ExtMonitorInfo,
    val monitorClick: String,
    val monitorClickList: List<Any>,
    val monitorImpress: String,
    val monitorImpressList: List<Any>,
    val pic: String,
    val pid: String,
    val requestId: String,
    val s_ctrp: String,
    val scm: String,
    val showAdTag: Boolean,
    val showContext: String,
    val targetId: Long,
    val targetType: Int,
    val titleColor: String,
    val typeTitle: String,
    val url: String
)
data class ExtMonitor(
    val monitorClick: String,
    val monitorImpress: String,
    val monitorType: String
)
data class ExtMonitorInfo(
    val ad_type: String,
    val aliAdId: String,
    val displayMd5Sign: String,
    val dspId: String,
    val mode: String,
    val monitorType: String,
    val needClickPosInfo: Boolean,
    val orderId: String,
    val price: String,
    val refer: String,
    val reqId: String,
    val requestid: String,
    val scm: String,
    val spm: String
)
data class Trp(
    val rules: List<String>
)