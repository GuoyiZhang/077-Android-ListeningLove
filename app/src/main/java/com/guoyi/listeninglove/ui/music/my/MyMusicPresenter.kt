package com.guoyi.listeninglove.ui.music.my

import com.guoyi.listeninglove.api.music.netease.NeteaseApiServiceImpl
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.api.net.RequestCallBack
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.data.PlayHistoryLoader
import com.guoyi.listeninglove.data.PlaylistLoader
import com.guoyi.listeninglove.data.SongLoader
import com.guoyi.listeninglove.data.VideoLoader
import com.guoyi.listeninglove.ui.music.edit.PlaylistManagerUtils
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.ui.download.DownloadLoader
import com.guoyi.listeninglove.ui.my.user.UserStatus
import com.guoyi.listeninglove.utils.LogUtil
import com.guoyi.listeninglove.utils.SPUtils
import com.guoyi.listeninglove.utils.ToastUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by yonglong on 2018/1/6.
 */

class MyMusicPresenter @Inject
constructor() : BasePresenter<MyMusicContract.View>(), MyMusicContract.Presenter {
    /**
     * 更新播放历史
     */
    fun updateHistory() {
        doAsync {
            val data = PlayHistoryLoader.getPlayHistory()
            uiThread {
                mView?.showHistory(data)
            }
        }
    }

    /**
     * 更新播放历史
     */
    private fun updateLocal() {
        doAsync {
            val data = SongLoader.getLocalMusic(mView.context)
            uiThread {
                mView?.showSongs(data)
            }
        }
    }

    /**
     * 更新本地歌单
     */
    fun updateFavorite() {
        doAsync {
            val data = SongLoader.getFavoriteSong()
            uiThread {
                mView?.showLoveList(data)
            }
        }
    }


    /**
     * 更新下载歌曲
     */
    fun updateDownload() {
        doAsync {
            val data = DownloadLoader.getDownloadList()
            uiThread {
                mView?.showDownloadList(data)
            }
        }
    }

    /**
     * 更新下载歌曲
     */
    fun updateLocalVideo() {
        doAsync {
            val musicList = VideoLoader.getAllLocalVideos(mView.context)
            uiThread {
                mView?.showVideoList(musicList)
            }
        }
    }


    override fun loadSongs() {
        updateLocal()
        updateHistory()
        updateFavorite()
        updateLocalVideo()
        updateDownload()
    }

    fun loadMusicLakeNotice() {
        PlaylistManagerUtils.getMusicNoticeInfo(
                success = {
                    mView?.showNoticeInfo(it)
                }, fail = {
        })
    }

    /**
     * 获取在线歌单
     */
    override fun loadPlaylist(playlist: Playlist?) {
        if (UserStatus.getLoginStatus() && UserStatus.getTokenStatus()) {
            val mIsLogin = UserStatus.getLoginStatus()
            if (mIsLogin) {
                PlaylistManagerUtils.getOnlinePlaylist(success = {
                    mView?.showPlaylist(it)
                }, fail = {
                    ToastUtils.show(it)
                    if (PlaylistManagerUtils.playlists.size == 0) {
                        mView?.showError(it, true)
                    }
                })
            } else {
                PlaylistManagerUtils.playlists.clear()
                mView?.showPlaylist(PlaylistManagerUtils.playlists)
            }
        } else {
            mView?.showPlaylist(mutableListOf())
        }
        loadWyUserPlaylist()
    }

    /**
     * 获取本地歌单
     */
    fun loadLocalPlaylist() {
        doAsync {
            val playlist = PlaylistLoader.getAllPlaylist()
            playlist.forEach {
                it.pid?.let { it1 ->
                    val list = PlaylistLoader.getMusicForPlaylist(it1)
                    it.total = list.size.toLong()
                }
            }
            uiThread {
                mView?.showLocalPlaylist(playlist)
            }
        }
    }


    /**
     * 加载网易云歌单
     */
    fun loadWyUserPlaylist() {
        val uid = SPUtils.getAnyByKey(SPUtils.SP_KEY_NETEASE_UID, "")
        LogUtil.d("MyMusic", "uid = $uid")
        if (uid.isEmpty()) return
        val observable = NeteaseApiServiceImpl.getUserPlaylist(uid = uid)
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                mView?.showWyPlaylist(result)
            }

            override fun error(msg: String) {
                mView?.showWyPlaylist(mutableListOf())
            }
        })
    }
}
