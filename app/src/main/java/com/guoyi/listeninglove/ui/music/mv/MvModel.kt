package com.guoyi.listeninglove.ui.music.mv

import com.guoyi.musicapi.netease.*
import com.guoyi.listeninglove.api.music.baidu.BaiduApiServiceImpl
import com.guoyi.listeninglove.api.music.kugou.KuGouApiServiceImpl
import com.guoyi.listeninglove.api.music.netease.NeteaseApiServiceImpl
import com.guoyi.listeninglove.bean.MvInfoBean
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.api.net.RequestCallBack

/**
 * Des    :
 * Author : master.
 * Date   : 2018/7/8 .
 */

class MvModel {
    /**
     * 加载mv详情
     */
    fun loadMvDetail(mvid: String?, result: RequestCallBack<MvDetailInfo>?) {
        val observable = mvid?.let { NeteaseApiServiceImpl.getMvDetailInfo(it) } ?: return
        ApiManager.request(observable, result)
    }

    /**
     *
     * 加载最新mv
     */
    fun loadRecentMv(limit: Int = 30, result: RequestCallBack<MvInfo>?) {
        val observable = NeteaseApiServiceImpl.getNewestMv(limit)
        ApiManager.request(observable, result)
    }

    /**
     *
     * 加载排行榜mv
     */
    fun loadMv(offset: Int, result: RequestCallBack<MvInfo>?) {
        val observable = NeteaseApiServiceImpl.getTopMv(50, offset)
        ApiManager.request(observable, result)
    }

    /**
     *
     * 加载推荐mv
     */
    fun loadPersonalizedMv( result: RequestCallBack<MvInfo>?) {
        val observable = NeteaseApiServiceImpl.personalizedMv()
        ApiManager.request(observable, result)
    }

    /**
     * 获取相似mv
     */
    fun loadSimilarMv(mvid: String?, result: RequestCallBack<SimilarMvInfo>?) {
        val observable = mvid?.let { NeteaseApiServiceImpl.getSimilarMv(it) } ?: return
        ApiManager.request(observable, result)
    }

    /**
     *获取mv数据
     */
    fun loadMvComment(mvid: String?, result: RequestCallBack<MvComment>?) {
        val observable = mvid?.let { NeteaseApiServiceImpl.getMvComment(it) } ?: return
        ApiManager.request(observable, result)
    }

    /**
     *搜索mv数据
     */
    fun searchMv(key: String, offset: Int, result: RequestCallBack<SearchInfo>?) {
        val observable = NeteaseApiServiceImpl.searchMoreInfo(key, 30, offset, 1004)
        ApiManager.request(observable, result)
    }

    fun loadBaiduMv(songId: String?, result: RequestCallBack<MvInfoBean>?) {
        val observable = BaiduApiServiceImpl.getMvInfo(songId)
        ApiManager.request(observable, result)
    }

    /**
     * 搜索酷狗mv
     */
    fun loadKugouMv(songId: String?, result: RequestCallBack<MvInfoBean>?) {
        val observable = KuGouApiServiceImpl.getMvInfo(songId)
        ApiManager.request(observable, result)
    }
}