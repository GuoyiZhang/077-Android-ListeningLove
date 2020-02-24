package com.guoyi.listeninglove.api.music.netease

import com.guoyi.musicapi.netease.*
import com.guoyi.listeninglove.api.music.MusicUtils
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.bean.Artist
import com.guoyi.listeninglove.bean.HotSearchBean
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.utils.LogUtil
import com.guoyi.listeninglove.utils.SPUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by D22434 on 2018/1/5.
 */

object NeteaseApiServiceImpl {
    private val TAG = "NeteaseApiServiceImpl"

    val apiService by lazy { ApiManager.getInstance().create(NeteaseApiService::class.java, SPUtils.getAnyByKey(SPUtils.SP_KEY_NETEASE_API_URL, Constants.BASE_NETEASE_URL)) }

    /**
     * 获取歌单歌曲
     */
    fun getTopArtists(limit: Int, offset: Int): Observable<MutableList<Artist>> {
        return apiService.getTopArtists(offset, limit)
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MutableList<Artist>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Artist>()
                                it.list.artists?.forEach {
                                    val playlist = Artist()
                                    playlist.artistId = it.id.toString()
                                    playlist.name = it.name
                                    playlist.picUrl = it.picUrl
                                    playlist.score = it.score
                                    playlist.musicSize = it.musicSize
                                    playlist.albumSize = it.albumSize
                                    playlist.type = Constants.NETEASE
                                    list.add(playlist)
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable("网络异常"))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     * 获取歌单歌曲数据
     */
    fun getTopPlaylists(cat: String, limit: Int): Observable<MutableList<Playlist>> {
        return apiService.getTopPlaylist(cat, limit)
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Playlist>()
                                it.playlists?.forEach {
                                    val playlist = Playlist()
                                    playlist.pid = it.id.toString()
                                    playlist.name = it.name
                                    playlist.coverUrl = it.coverImgUrl
                                    playlist.des = it.description
                                    playlist.date = it.createTime
                                    playlist.updateDate = it.updateTime
                                    playlist.playCount = it.playCount.toLong()
                                    playlist.type = Constants.PLAYLIST_WY_ID
                                    list.add(playlist)
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable("网络异常"))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     * 获取歌单歌曲数据
     */
    fun getTopPlaylistsHigh(tag: String, limit: Int, before: Long?): Observable<MutableList<Playlist>> {
        val map = mutableMapOf<String, Any>()
        map["cat"] = tag
        map["limit"] = limit
        before?.let {
            map["before"] = it
        }
        return apiService.getTopPlaylistHigh(map)
                .flatMap {
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Playlist>()
                                it.playlists?.forEach {
                                    val playlist = Playlist()
                                    playlist.pid = it.id.toString()
                                    playlist.name = it.name
                                    playlist.coverUrl = it.coverImgUrl
                                    playlist.des = it.description
                                    playlist.date = it.createTime
                                    playlist.updateDate = it.updateTime
                                    playlist.playCount = it.playCount.toLong()
                                    playlist.type = Constants.PLAYLIST_WY_ID
                                    list.add(playlist)
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable("网络异常"))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     * 获取精品歌单歌曲数据
     */
    fun getPlaylistDetail(id: String): Observable<Playlist> {
        return apiService.getPlaylistDetail(id)
                .flatMap {
                    Observable.create(ObservableOnSubscribe<Playlist> { e ->
                        try {
                            if (it.code == 200) {
                                it.playlist?.let {
                                    val playlist = Playlist()
                                    playlist.pid = it.id.toString()
                                    playlist.name = it.name
                                    playlist.coverUrl = it.coverImgUrl
                                    playlist.des = it.description
                                    playlist.date = it.createTime
                                    playlist.updateDate = it.updateTime
                                    playlist.playCount = it.playCount.toLong()
                                    playlist.type = Constants.PLAYLIST_WY_ID
                                    playlist.musicList = MusicUtils.getNeteaseMusicList(it.tracks)
                                    e.onNext(playlist)
                                }
                                e.onComplete()
                            } else {
                                e.onError(Throwable("网络异常"))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     * 获取最新mv
     */
    fun getNewestMv(limit: Int): Observable<MvInfo> {
        return apiService.getNewestMv(limit)
    }

    /**
     * 获取排行榜mv
     */
    fun getTopMv(limit: Int, offset: Int): Observable<MvInfo> {
        return apiService.getTopMv(offset, limit)
    }

    /**
     * 获取mv信息
     */
    fun getMvDetailInfo(mvid: String): Observable<MvDetailInfo> {
        return apiService.getMvDetailInfo(mvid)
    }

    /**
     * 获取相似mv
     */
    fun getSimilarMv(mvid: String): Observable<SimilarMvInfo> {
        return apiService.getSimilarMv(mvid)
    }

    /**
     * 获取mv评论
     */
    fun getMvComment(mvid: String): Observable<MvComment> {
        return apiService.getMvComment(mvid)
    }

    /**
     * 获取热搜
     */
    fun getHotSearchInfo(): Observable<MutableList<HotSearchBean>> {
        return apiService.getHotSearchInfo()
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MutableList<HotSearchBean>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<HotSearchBean>()
                                it.result.hots?.forEach {
                                    list.add(HotSearchBean(it.first))
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable("网络异常"))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     * 搜索
     */
    fun searchMoreInfo(keywords: String, limit: Int, offset: Int, type: Int): Observable<SearchInfo> {
        val url = SPUtils.getAnyByKey(SPUtils.SP_KEY_NETEASE_API_URL, Constants.BASE_NETEASE_URL) + "/search?keywords= $keywords&limit=$limit&offset=$offset&type=$type"
//        return apiService.searchNetease(url)
//        @Query("keywords") keywords: String, @Query("limit") limit: Int, @Query("offset") offset: Int, @Query("type") type: Int
        return apiService.searchNetease(url)
    }

    /**
     * 获取风格
     */
    fun getCatList(): Observable<CatListBean> {
        return apiService.getCatList()
    }

    /**
     * 获取banner
     */
    fun getBanners(): Observable<BannerResult> {
        return apiService.getBanner()
    }

    /**
     *登录
     */
    fun loginPhone(username: String, pwd: String, isEmail: Boolean): Observable<LoginInfo> {
        return if (isEmail)
            apiService.loginEmail(username, pwd)
        else
            apiService.loginPhone(username, pwd)
    }

    /**
     * 获取登录状态
     */
    fun getLoginStatus(): Observable<LoginInfo> {
        return apiService.getLoginStatus()
    }

    /**
     * 注销绑定
     */
    fun logout(): Observable<Any> {
        return apiService.logout()
    }

    /**
     *推荐歌曲
     */
    fun recommendSongs(): Observable<MutableList<Music>> {
        return apiService.recommendSongs()
                .flatMap {
                    Observable.create(ObservableOnSubscribe<MutableList<Music>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Music>()
                                list.addAll(MusicUtils.getNeteaseRecommendMusic(it.recommend))
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable(it.msg))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }


    /**
     *每日推荐歌单
     */
    fun recommendPlaylist(): Observable<MutableList<Playlist>> {
        return apiService.recommendPlaylist()
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Playlist>()
                                it.recommend?.forEach {
                                    val playlist = Playlist()
                                    playlist.pid = it.id.toString()
                                    playlist.name = it.name
                                    playlist.coverUrl = if (it.coverImgUrl != null) it.coverImgUrl else it.creator.avatarUrl
                                    playlist.des = it.description
                                    playlist.date = it.createTime
                                    playlist.updateDate = it.updateTime
                                    playlist.playCount = it.playCount.toLong()
                                    playlist.type = Constants.PLAYLIST_WY_ID
                                    list.add(playlist)
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable(it.msg))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     *推荐歌单
     */
    fun personalizedPlaylist(): Observable<MutableList<Playlist>> {
        return apiService.personalizedPlaylist()
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Playlist>()
                                it.result?.forEach {
                                    val playlist = Playlist()
                                    playlist.pid = it.id.toString()
                                    playlist.name = it.name
                                    playlist.coverUrl = it.picUrl
                                    playlist.des = it.copywriter
                                    playlist.playCount = it.playCount.toLong()
                                    playlist.type = Constants.PLAYLIST_WY_ID
                                    list.add(playlist)
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable(""))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }


    /**
     *推荐mv
     */
    fun personalizedMv(): Observable<MvInfo> {
        return apiService.personalizedMv()
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MvInfo> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<MvInfoDetail>()
                                it.result?.forEach {
                                    val data = MvInfoDetail(
                                            artistId = it.artistId,
                                            id = it.id.toInt(),
                                            artistName = it.artistName,
                                            artists = it.artists,
                                            cover = it.picUrl,
                                            playCount = it.playCount.toInt(),
                                            duration = it.duration,
                                            desc = it.copywriter,
                                            name = it.name

                                    )
                                    list.add(data)
                                }
                                val mvInfo = MvInfo(code = 200, hasMore = false, updateTime = 0, data = list)
                                e.onNext(mvInfo)
                                e.onComplete()
                            } else {
                                e.onError(Throwable(""))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     *获取用户歌单
     */
    fun getUserPlaylist(uid: String): Observable<MutableList<Playlist>> {
        return apiService.getUserPlaylist(uid)
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Playlist>()
                                it.playlist?.forEach {
                                    val playlist = Playlist()
                                    playlist.pid = it.id.toString()
                                    playlist.name = it.name
                                    playlist.coverUrl = it.coverImgUrl
                                    playlist.des = it.description
                                    playlist.date = it.createTime
                                    playlist.updateDate = it.updateTime
                                    playlist.total = it.trackCount.toLong()
                                    playlist.playCount = it.playCount.toLong()
                                    playlist.type = Constants.PLAYLIST_WY_ID
                                    list.add(playlist)
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                e.onError(Throwable("网络异常"))
                            }
                        } catch (ep: Exception) {
                            e.onError(ep)
                        }
                    })
                }
    }


    /**
     *获取网易云排行榜歌单
     */
    fun getTopList(): Observable<MutableList<Playlist>> {
        return apiService.getTopList()
                .flatMap { it ->
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> { e ->
                        try {
                            if (it.code == 200) {
                                val list = mutableListOf<Playlist>()
                                LogUtil.d(TAG, "playlist= ${it.list.size}")
                                it.list.forEach {
                                    val playlist = Playlist()
                                    playlist.pid = it.id.toString()
                                    playlist.name = it.name
                                    playlist.updateFrequency = it.updateFrequency
                                    playlist.coverUrl = it.coverImgUrl
                                    playlist.des = it.description
                                    playlist.date = it.createTime
                                    playlist.updateDate = it.updateTime
                                    playlist.total = it.trackCount
                                    playlist.playCount = it.playCount
                                    playlist.type = Constants.PLAYLIST_WY_ID
                                    if (it.ToplistType != null) {
                                        LogUtil.d(TAG, "type = ${it.ToplistType} ${it.tracks} ")
                                        val musicList = mutableListOf<Music>()
                                        it.tracks?.forEach { track ->
                                            val music = Music()
                                            music.title = track.first
                                            music.artist = track.second
                                            musicList.add(music)
                                        }
                                        playlist.musicList = musicList
                                    }
                                    LogUtil.d(TAG, "playlist = $playlist ")
                                    list.add(playlist)
                                }
                                e.onNext(list)
                                e.onComplete()
                            } else {
                                LogUtil.d(TAG, "网络异常= ${it.list.size}")
                                e.onError(Throwable("网络异常"))
                            }
                        } catch (ep: Exception) {
                            LogUtil.d(TAG, "Exception= ${ep.message}")
                            e.onError(ep)
                        }
                    })
                }
    }

    /**
     * 获取私人FM
     */
//    fun getPersonalFm(): Observable<Playlist> {
//        return apiService.getPersonlFM()
//                .flatMap { it ->
//                    Observable.create(ObservableOnSubscribe<Playlist> { e ->
//                        try {
//                            if (it.code == 200) {
//                                val playlist = Playlist().apply {
//                                    name = "私人FM"
//                                    pid = "personal_fm"
//                                }
//                                val musicList = mutableListOf<Music>()
//                                it.data?.forEach {
//                                    val music = Music()
//                                    music.mid = it.id.toString()
//                                    music.title = it.name
//                                    it.artists?.forEach { item ->
//                                        music.artist += item.name
//                                        music.artistId += item.id
//                                    }
//                                    music.coverUri = it.album.picUrl
//                                    music.album = it.album.name
//                                    music.albumId = it.album.id
//                                    music.type = Constants.NETEASE
//                                    musicList.add(music)
//                                }
//                                playlist.musicList = musicList
//                                e.onNext(playlist)
//                                e.onComplete()
//                            } else {
//                                LogUtil.d(TAG, "getPersonalFm 网络异常= $it")
//                                e.onError(Throwable("网络异常"))
//                            }
//                        } catch (ep: Exception) {
//                            LogUtil.d(TAG, "Exception= ${ep.message}")
//                            e.onError(ep)
//                        }
//                    })
//                }
//    }

}