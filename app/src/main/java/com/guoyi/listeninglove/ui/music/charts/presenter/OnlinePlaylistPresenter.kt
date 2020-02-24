package com.guoyi.listeninglove.ui.music.charts.presenter

import com.guoyi.listeninglove.api.playlist.PlaylistApiServiceImpl
import com.guoyi.listeninglove.api.music.baidu.BaiduApiServiceImpl
import com.guoyi.listeninglove.api.music.netease.NeteaseApiServiceImpl
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.api.net.RequestCallBack
import com.guoyi.listeninglove.ui.music.charts.ChartsAdapter
import com.guoyi.listeninglove.ui.music.charts.GroupItemData
import com.guoyi.listeninglove.ui.music.charts.contract.OnlinePlaylistContract
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by D22434 on 2018/1/4.
 */

class OnlinePlaylistPresenter @Inject
constructor() : BasePresenter<OnlinePlaylistContract.View>(), OnlinePlaylistContract.Presenter {
    override fun loadQQList() {
        val observable = PlaylistApiServiceImpl.getQQRank(3)
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                val data = mutableListOf<GroupItemData>()
                data.add(GroupItemData("QQ音乐官方榜单"))
                result.forEach {
                    val item = GroupItemData(it)
                    if (it.musicList.size > 0) {
                        item.itemType = ChartsAdapter.ITEM_CHART_LARGE
                    }
                    data.add(item)
                }
                mView?.showQQCharts(data)
            }

            override fun error(msg: String) {
                mView?.hideLoading()
            }
        })
    }

    override fun loadBaiDuPlaylist() {
        BaiduApiServiceImpl.getOnlinePlaylist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindToLife())
                .subscribe(object : Observer<List<Playlist>> {
                    override fun onSubscribe(d: Disposable) {
                        mView.showLoading()
                    }

                    override fun onNext(result: List<Playlist>) {
                        val data = mutableListOf<GroupItemData>()
                        data.add(GroupItemData("百度音乐榜单"))
                        result.forEach {
                            val item = GroupItemData(it)
                            item.itemType = ChartsAdapter.ITEM_CHART_LARGE
                            data.add(item)
                        }
                        mView.showBaiduCharts(data)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        mView.hideLoading()
                        mView.showErrorInfo(e.message)
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }
                })
    }

    /**
     * 加载网易云歌单
     */
    override fun loadTopList() {
        val observable = PlaylistApiServiceImpl.getNeteaseRank(IntArray(22) { i -> i }, 3)
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                val data = mutableListOf<GroupItemData>()
                data.add(GroupItemData("QQ音乐官方榜单"))
                result.forEach {
                    val item = GroupItemData(it)
                    item.itemType = ChartsAdapter.ITEM_CHART
                    data.add(item)
                }
                mView?.showNeteaseCharts(data)
            }

            override fun error(msg: String) {
                mView.hideLoading()
            }
        })
    }

    /**
     * 加载Netease网易云歌单
     */
    fun loadNeteaseTopList() {
        val observable = NeteaseApiServiceImpl.getTopList()
        ApiManager.request(observable, object : RequestCallBack<MutableList<Playlist>> {
            override fun success(result: MutableList<Playlist>) {
                val data = mutableListOf<GroupItemData>()
                data.add(GroupItemData("网易云音乐官方榜单"))
                var flag = true
                result.forEach {
                    val item = GroupItemData(it)
                    if (it.musicList.size > 0) {
                        item.itemType = ChartsAdapter.ITEM_CHART_LARGE
                    }
                    if (it.musicList.size == 0 && flag) {
                        data.add(GroupItemData("网易云音乐更多榜单"))
                        flag = false
                    }
                    data.add(item)
                }
                mView?.showNeteaseCharts(data)
            }

            override fun error(msg: String) {
                mView?.hideLoading()
            }
        })
    }
}
