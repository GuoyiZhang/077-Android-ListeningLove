package com.guoyi.listeninglove.ui.music.discover

import com.guoyi.musicapi.netease.BannerBean
import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.bean.Artist
import com.guoyi.listeninglove.bean.Music


interface DiscoverContract {

    interface View : BaseContract.BaseView {
        fun showEmptyView(msg: String)

        fun showBaiduCharts(charts: MutableList<Playlist>)

        fun showNeteaseCharts(charts: MutableList<Playlist>)

        fun showArtistCharts(charts: MutableList<Artist>)

        fun showRadioChannels(channels: MutableList<Playlist>)

        fun showBannerView(banners: MutableList<BannerBean>)

        fun showRecommendPlaylist(playlists: MutableList<Playlist>)

        fun showRecommendSongs(songs: MutableList<Music>)

        fun showPersonalFm(playlist: Playlist)

    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadBaidu()

        fun loadNetease(tag: String)

        fun loadArtists()

        fun loadRaios()
    }
}
