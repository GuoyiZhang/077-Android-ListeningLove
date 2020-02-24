package com.guoyi.listeninglove.ui.music.charts

import com.guoyi.listeninglove.api.music.baidu.BaiduApiServiceImpl
import com.guoyi.listeninglove.api.music.netease.NeteaseApiServiceImpl
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.api.net.RequestCallBack
import com.guoyi.listeninglove.api.playlist.PlaylistApiServiceImpl
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by D22434 on 2018/1/4.
 */

class PlaylistPresenter @Inject
constructor() : BasePresenter<PlaylistContract.View>(), PlaylistContract.Presenter {


    /**
     *根据歌单id获取歌单详情
     */
    override fun loadNeteasePlaylist(id: String) {
        mView?.showLoading()
        val observable = NeteaseApiServiceImpl.getPlaylistDetail(id)
        ApiManager.request(observable, object : RequestCallBack<Playlist> {
            override fun success(result: Playlist) {
                mView?.hideLoading()
                mView?.showPlayList(result)
            }

            override fun error(msg: String) {
                mView?.showError(msg, true)
                mView?.hideLoading()
            }
        })
    }

    /**
     * 自身的服务器
     * 获取排行榜歌单
     */
    override fun loadPlaylist(idx: String, type: String?) {
        if (type == Constants.PLAYLIST_WY_ID) {
            loadNeteasePlaylist(idx)
            return
        }

        mView?.showLoading()
        val observable = PlaylistApiServiceImpl.getRankDetailInfo(intArrayOf(idx.toInt()), null, type)
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.hideLoading()
                mView?.showPlayList(result.first())
            }

            override fun error(msg: String) {
                mView?.showError(msg, true)
                mView?.hideLoading()
            }
        })
    }

    /**
     * 获取百度音乐排行榜音乐
     */
    override fun loadOnlineMusicList(type: String, limit: Int, mOffset: Int) {
        mView?.showLoading()
        ApiManager.request(BaiduApiServiceImpl.getOnlineSongs(type, limit, mOffset), object : RequestCallBack<MutableList<Music>> {
            override fun error(msg: String?) {
                mView?.hideLoading()
                mView?.showError(msg, true)
            }

            override fun success(result: MutableList<Music>?) {
                result?.forEach {
                    if (it.isCp)
                        result.remove(it)
                }
                mView?.showOnlineMusicList(result)
                mView?.hideLoading()
            }

        })
    }

    /**
     * 加载网易排行榜（0歌曲）
     */
    fun loadNetease(tag: String) {
        val observable = NeteaseApiServiceImpl.getTopPlaylists(tag, 30)
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showNeteaseCharts(result)
            }

            override fun error(msg: String) {
                mView?.showNeteaseCharts(mutableListOf())
            }
        })
    }

    /**
     * 加载网易排行榜（0歌曲）
     */
    fun loadHighQualityPlaylist(tag: String) {
        val observable = NeteaseApiServiceImpl.getTopPlaylistsHigh(tag, 30, null)
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showNeteaseCharts(result)
            }

            override fun error(msg: String) {
                mView?.showNeteaseCharts(mutableListOf())
            }
        })
    }

}
