package com.guoyi.listeninglove.ui.music.discover

import com.guoyi.musicapi.netease.BannerResult
import com.guoyi.listeninglove.api.music.baidu.BaiduApiServiceImpl
import com.guoyi.listeninglove.api.music.netease.NeteaseApiServiceImpl
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.bean.Artist
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.api.net.RequestCallBack
import javax.inject.Inject

/**
 * Des    :
 * Author : master.
 * Date   : 2018/5/20 .
 */
class DiscoverPresenter @Inject
constructor() : BasePresenter<DiscoverContract.View>(), DiscoverContract.Presenter {

    override fun loadBaidu() {
        ApiManager.request(BaiduApiServiceImpl.getOnlinePlaylist(), object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showBaiduCharts(result)
            }

            override fun error(msg: String) {
                mView?.showEmptyView(msg)
                mView?.showBaiduCharts(mutableListOf())
            }
        })
    }

    /**
     * 加载网易排行榜（0歌曲）
     */
    override fun loadNetease(tag: String) {
        val observable = NeteaseApiServiceImpl.getTopPlaylists(tag, 30)
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showNeteaseCharts(result)
            }

            override fun error(msg: String) {
                mView?.showEmptyView(msg)
                mView?.showNeteaseCharts(mutableListOf())
            }
        })
    }

    override fun loadArtists() {
        ApiManager.request(NeteaseApiServiceImpl.getTopArtists(30, 0), object : RequestCallBack<MutableList<Artist>> {
            override fun success(result: MutableList<Artist>) {
                mView?.showArtistCharts(result)
            }

            override fun error(msg: String) {
                mView?.showEmptyView(msg)
                mView?.showArtistCharts(mutableListOf())
            }
        })
    }

    override fun loadRaios() {
        loadBannerView()
        val observable = BaiduApiServiceImpl.getRadioChannel()
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showRadioChannels(result)
            }

            override fun error(msg: String) {
                mView?.showEmptyView(msg)
                mView?.showRadioChannels(mutableListOf())
            }
        })
    }

    fun loadBannerView() {
        val observable = NeteaseApiServiceImpl.getBanners()
        ApiManager.request(observable, object : RequestCallBack<BannerResult> {
            override fun success(result: BannerResult) {
                if (result.code == 200) {
                    mView?.showBannerView(result.banners)
                } else {
                    mView?.showBannerView(mutableListOf())
                }
            }

            override fun error(msg: String) {
                mView?.showEmptyView(msg)
                mView?.showBannerView(mutableListOf())
            }
        })
    }

    /**
     * 获取每日推荐（需登录）
     */
    fun loadRecommendPlaylist() {
        val observable = NeteaseApiServiceImpl.recommendPlaylist()
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showRecommendPlaylist(result)
            }

            override fun error(msg: String) {
                mView?.showEmptyView(msg)
                mView?.showRecommendPlaylist(mutableListOf())
            }
        })
    }

    /**
     * 获取推荐歌单
     */
    fun loadPersonalizedPlaylist() {
        val observable = NeteaseApiServiceImpl.personalizedPlaylist()
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showRecommendPlaylist(result)
            }

            override fun error(msg: String) {
                mView?.showEmptyView(msg)
                mView?.showRecommendPlaylist(mutableListOf())
            }
        })
    }

    /**
     * 获取私人电台
     */
    fun loadPersonalFM() {
//        val observable = NeteaseApiServiceImpl.getPersonalFm()
//        ApiManager.request(observable, object : RequestCallBack<Playlist> {
//            override fun success(result: Playlist) {
//                mView?.showPersonalFm(result)
//                LogUtil.d(result.toString())
//            }
//
//            override fun error(msg: String) {
//                ToastUtils.show(msg)
//            }
//        })
    }
}
