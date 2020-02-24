package com.guoyi.listeninglove.ui.music.local.contract

import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.bean.Music

interface SongsContract {

    interface View : BaseContract.BaseView {
        fun showSongs(songList: MutableList<Music>)

        fun setEmptyView()
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadSongs(isReload: Boolean)
    }
}
