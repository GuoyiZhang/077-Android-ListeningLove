package com.guoyi.listeninglove.ui.music.discover.artist

import com.guoyi.musicapi.bean.SingerTag
import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.bean.Artist


interface ArtistListContract {

    interface View : BaseContract.BaseView {
        fun showArtistList(artistList: MutableList<Artist>)
        fun showArtistTags(tags: SingerTag)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadArtists(offset: Int, params: Map<String,Int>)
    }
}
