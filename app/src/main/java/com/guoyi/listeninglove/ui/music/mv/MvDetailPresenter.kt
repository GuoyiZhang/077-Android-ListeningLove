package com.guoyi.listeninglove.ui.music.mv

import com.guoyi.musicapi.netease.MvComment
import com.guoyi.musicapi.netease.MvDetailInfo
import com.guoyi.musicapi.netease.MvInfo
import com.guoyi.musicapi.netease.SimilarMvInfo
import com.guoyi.listeninglove.bean.MvInfoBean
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.api.net.RequestCallBack
import javax.inject.Inject

/**
 * Des    :
 * Author : master.
 * Date   : 2018/5/20 .
 */
class MvDetailPresenter @Inject
constructor() : BasePresenter<MvDetailContract.View>(), MvDetailContract.Presenter {
    override fun loadBaiduMvInfo(songId: String?) {
        mvModel.loadBaiduMv(songId, object : RequestCallBack<MvInfoBean> {
            override fun success(result: MvInfoBean?) {
                mView?.hideLoading()
                mView?.showBaiduMvDetailInfo(result)
            }

            override fun error(msg: String?) {
                mView?.hideLoading()
                mView?.showError(msg, true)
            }

        })
    }

    override fun loadKugouMvInfo(songId: String?) {
        mvModel.loadKugouMv(songId, object : RequestCallBack<MvInfoBean> {
            override fun success(result: MvInfoBean?) {
                mView?.hideLoading()
                mView?.showBaiduMvDetailInfo(result)
            }

            override fun error(msg: String?) {
                mView?.hideLoading()
                mView?.showError(msg, true)
            }

        })
    }

    private val mvModel = MvModel()

    override fun loadMvDetail(mvid: String?) {
        mvModel.loadMvDetail(mvid, object : RequestCallBack<MvDetailInfo> {
            override fun success(result: MvDetailInfo?) {
                mView?.hideLoading()
                result?.data?.let {
                    mView?.showMvDetailInfo(it)
                }
            }

            override fun error(msg: String?) {
                mView?.hideLoading()
                mView?.showError(msg, true)
            }

        })
    }

    override fun loadMv(offset: Int) {
        mvModel.loadMv(offset, object : RequestCallBack<MvInfo> {
            override fun success(result: MvInfo?) {
                result?.data?.let {
                    mView?.showMvList(it)
                }
            }

            override fun error(msg: String?) {
            }

        })
    }

    override fun loadSimilarMv(mvid: String?) {
        mvModel.loadSimilarMv(mvid, object : RequestCallBack<SimilarMvInfo> {
            override fun success(result: SimilarMvInfo?) {
                result?.data?.let {
                    mView?.showMvList(it)
                }
            }

            override fun error(msg: String?) {
            }

        })
    }

    override fun loadMvComment(mvid: String?) {
        mvModel.loadMvComment(mvid, object : RequestCallBack<MvComment> {
            override fun success(result: MvComment?) {
                result?.comments?.let {
                    mView?.showMvComment(it)
                }
                result?.hotComments?.let {
                    mView?.showMvHotComment(it)
                }
            }

            override fun error(msg: String?) {
            }

        })
    }
}
