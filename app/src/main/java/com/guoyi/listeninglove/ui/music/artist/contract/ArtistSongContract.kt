package com.guoyi.listeninglove.ui.music.artist.contract

import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.ui.base.BaseContract

interface ArtistSongContract {

    interface View : BaseContract.BaseView {

        fun showEmptyView()

        fun showSongs(songList: MutableList<Music>)
    }

    interface Presenter : BaseContract.BasePresenter<View> {

        fun loadSongs(artistName: String)
    }

}
