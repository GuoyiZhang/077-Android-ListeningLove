package com.guoyi.listeninglove.ui.music.charts.activity

import android.os.Bundle

import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.common.Extras
import com.guoyi.listeninglove.utils.LogUtil

/**
 * 作者：yonglong on 2016/8/24 10:43
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class NeteasePlaylistActivity : BasePlaylistActivity() {
    override fun setEnableMore(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mPlaylist == null) {
            mPlaylist = intent.getParcelableExtra(Extras.PLAYLIST)
        }
        mPlaylist?.pid?.let {
            mPresenter?.loadPlaylist(it, mPlaylist?.type)
        }
        LogUtil.d(TAG,"mPlaylist "+mPlaylist.toString())
    }

    override fun retryLoading() {
        super.retryLoading()
        mPlaylist?.pid?.let { mPresenter?.loadPlaylist(it, mPlaylist?.type) }
    }

    override fun getToolBarTitle(): String? {
        if (mPlaylist == null) {
            mPlaylist = intent.getParcelableExtra(Extras.PLAYLIST)
        }
        return mPlaylist?.name
    }

    override fun getmPlaylist(): Playlist? {
        if (mPlaylist == null) {
            mPlaylist = intent.getParcelableExtra(Extras.PLAYLIST)
        }
        return mPlaylist
    }

    companion object {
        private val TAG = "NeteasePlaylistActivity"
    }
}
