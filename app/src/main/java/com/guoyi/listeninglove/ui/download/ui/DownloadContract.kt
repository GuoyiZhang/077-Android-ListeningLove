package com.guoyi.listeninglove.ui.download.ui


import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.ui.download.TasksManagerModel

interface DownloadContract {

    interface View : BaseContract.BaseView {
        fun showErrorInfo(msg: String)

        fun showSongs(musicList: List<Music>)

        fun showDownloadList(modelList: List<TasksManagerModel>)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadDownloadMusic(isCache: Boolean)

        fun loadDownloading()
    }
}
