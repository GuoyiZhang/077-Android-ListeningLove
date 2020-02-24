package com.guoyi.listeninglove.ui.music.discover.artist

import com.guoyi.musicapi.bean.Artists
import com.guoyi.listeninglove.api.music.MusicApiServiceImpl
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.bean.Artist
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.api.net.RequestCallBack
import javax.inject.Inject

/**
 * Des    :
 * Author : master.
 * Date   : 2018/5/20 .
 */
class ArtistListPresenter @Inject
constructor() : BasePresenter<ArtistListContract.View>(), ArtistListContract.Presenter {

    override fun loadArtists(offset: Int, params: Map<String, Int>) {
        mView?.showLoading()
        ApiManager.request(MusicApiServiceImpl.getArtists(offset, params), object : RequestCallBack<Artists> {
            override fun success(result: Artists) {
                val artists = mutableListOf<Artist>()
                result.singerList.forEach {
                    val artist = Artist().apply {
                        name = it.singer_name
                        artistId = it.singer_id
                        picUrl = it.singer_pic
                        type = Constants.QQ
                    }
                    artists.add(artist)
                }
                mView?.hideLoading()
                mView?.showArtistList(artists)
                mView?.showArtistTags(result.tags)
            }

            override fun error(msg: String) {
                mView?.hideLoading()
                mView?.showError(msg, true)
            }
        })
    }

}
