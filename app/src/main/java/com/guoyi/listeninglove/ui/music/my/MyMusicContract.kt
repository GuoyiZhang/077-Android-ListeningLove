package com.guoyi.listeninglove.ui.music.my

import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.NoticeInfo
import com.guoyi.listeninglove.bean.Playlist

interface MyMusicContract {

    interface View : BaseContract.BaseView {

        fun showSongs(songList: MutableList<Music>)

        fun showVideoList(videoList: MutableList<Music>)

        fun showLocalPlaylist(playlists: MutableList<Playlist>)

        fun showPlaylist(playlists: MutableList<Playlist>)

        fun showWyPlaylist(playlists: MutableList<Playlist>)

        fun showHistory(musicList: MutableList<Music>)

        fun showLoveList(musicList: MutableList<Music>)

        fun showDownloadList(musicList: MutableList<Music>)

        fun showNoticeInfo(notice: NoticeInfo)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadSongs()

        fun loadPlaylist(playlist: Playlist? = null)
    }
}
