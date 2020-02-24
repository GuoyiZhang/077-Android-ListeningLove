package com.guoyi.listeninglove.ui.music.local.presenter

import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.bean.FolderInfo
import com.guoyi.listeninglove.data.AppRepository
import com.guoyi.listeninglove.data.SongLoader
import com.guoyi.listeninglove.ui.music.local.contract.FoldersContract
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by D22434 on 2018/1/8.
 */

class FoldersPresenter @Inject
constructor() : BasePresenter<FoldersContract.View>(), FoldersContract.Presenter {
    override fun loadSongs(path: String) {
        mView?.showLoading()
        doAsync {
            val musicList = SongLoader.getSongListInFolder(mView.context, path)
            uiThread {
                mView?.hideLoading()
                mView?.showSongs(musicList)
            }

        }
    }

    override fun loadFolders() {
        mView.showLoading()
        AppRepository.getFolderInfosRepository(mView.context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindToLife())
                .subscribe(object : Observer<List<FolderInfo>> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(folderInfos: List<FolderInfo>) {
                        mView.showFolders(folderInfos)
                        if (folderInfos.isEmpty()) {
                            mView.showEmptyView()
                        }
                        mView.hideLoading()
                    }

                    override fun onError(e: Throwable) {
                        mView.hideLoading()
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }
                })

    }

}
