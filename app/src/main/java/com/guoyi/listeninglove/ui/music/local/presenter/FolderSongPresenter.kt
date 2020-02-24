package com.guoyi.listeninglove.ui.music.local.presenter

import com.guoyi.listeninglove.data.VideoLoader
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.ui.music.local.contract.FolderSongsContract
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by D22434 on 2018/1/8.
 */

class FolderSongPresenter @Inject
constructor() : BasePresenter<FolderSongsContract.View>(), FolderSongsContract.Presenter {

    override fun loadSongs(path: String) {
        doAsync {
            val musicList = VideoLoader.getAllLocalVideos(mView.context)
            uiThread {
                mView?.showSongs(musicList)
            }
        }
    }
}
