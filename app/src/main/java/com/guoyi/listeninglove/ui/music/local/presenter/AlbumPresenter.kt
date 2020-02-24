package com.guoyi.listeninglove.ui.music.local.presenter

import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.data.SongLoader
import com.guoyi.listeninglove.ui.music.local.contract.AlbumsContract
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject


/**
 * Created by yonglong on 2018/1/7.
 */

class AlbumPresenter @Inject
constructor() : BasePresenter<AlbumsContract.View>(), AlbumsContract.Presenter {
    override fun loadAlbums(action: String) {
        mView?.showLoading()
        doAsync {
            val data = SongLoader.getAllAlbums()
            uiThread {
                mView?.showAlbums(data)
                mView?.hideLoading()
            }
        }
    }
}
