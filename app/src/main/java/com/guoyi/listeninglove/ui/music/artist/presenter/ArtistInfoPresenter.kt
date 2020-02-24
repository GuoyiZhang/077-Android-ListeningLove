package com.guoyi.listeninglove.ui.music.artist.presenter

import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.ui.music.artist.contract.ArtistInfoContract

import javax.inject.Inject

/**
 * Created by D22434 on 2018/1/4.
 */

class ArtistInfoPresenter @Inject
constructor() : BasePresenter<ArtistInfoContract.View>(), ArtistInfoContract.Presenter {

    override fun loadArtistInfo(music: Music) {
        val info = music.title + "-" + music.artist

    }
}
