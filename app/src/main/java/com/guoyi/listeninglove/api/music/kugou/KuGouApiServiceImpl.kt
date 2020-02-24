package com.guoyi.listeninglove.api.music.kugou

import com.guoyi.musicapi.kugou.Candidates
import com.guoyi.musicapi.kugou.KuGouApiService
import com.guoyi.musicapi.kugou.KugouLyric
import com.guoyi.listeninglove.api.music.MusicUtils
import com.guoyi.listeninglove.api.music.baidu.BaiduApiServiceImpl
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.data.SongLoader
import com.guoyi.listeninglove.utils.LyricUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by master on 2018/4/30.
 */

object KuGouApiServiceImpl {

    private val TAG = "KuGouApiServiceImpl"

    val searchApiService by lazy { ApiManager.getInstance().create(KuGouApiService::class.java, Constants.SEARCH_KUGOU_URL) }
    val infoApiService by lazy { ApiManager.getInstance().create(KuGouApiService::class.java, Constants.INFO_KUGOU_URL) }
    val baseApiService by lazy { ApiManager.getInstance().create(KuGouApiService::class.java, Constants.BASE_KUGOU_URL) }
    val lrcService by lazy { ApiManager.getInstance().create(KuGouApiService::class.java, Constants.LRC_KUGOU_URL) }

    /**
     * 搜索歌词
     */
    fun searchLyric(title: String, duration: Long): Observable<KugouLyric> {
        return lrcService.searchLyric(title, duration.toString())
    }

    /**
     * 获取歌词
     */
    fun getKugouLyricInfo(candidates: Candidates?): Observable<String>? {
        if (candidates?.id == null) {
            return null
        }
        return Observable.create { result ->
            lrcService.getRawLyric(candidates.id, candidates.accesskey).subscribe {
                if (it?.status == 200) {
                    val tt = LyricUtil.decryptBASE64(it.content)
                    result.onNext(tt)
                    result.onComplete()
                } else {
                    result.onError(Throwable())
                }
            }
        }
    }

    /**
     * 搜索酷狗音乐
     */
    fun searchMusic(query: String, limit: Int, offset: Int): Observable<MutableList<Music>>? {
        return searchApiService.queryMerge(query, offset + 1, limit)
                .flatMap {
                    Observable.create(ObservableOnSubscribe<MutableList<Music>> { e ->
                        val musicList = mutableListOf<Music>()
                        try {
                            if (it.status == 1) {
                                it.data.info.forEach { song ->
                                    val musicInfo = Music()
                                    musicInfo.mid = song.hash
                                    musicInfo.type = Constants.KUGOU
                                    musicInfo.title = song.songname
                                    musicInfo.artist = song.singername
                                    musicInfo.artistId = song.album_id
                                    musicInfo.album = song.songname
                                    musicInfo.albumId = song.album_id
                                    musicInfo.hasMv = if (song.mvhash == "") 0 else 1
                                    musicInfo.coverUri = ""
                                    musicList.add(musicInfo)
                                }
                            }
                        } catch (error: Exception) {
                            e.onError(Throwable(error.message))
                        }
                        e.onNext(musicList)
                        e.onComplete()
                    })
                }
    }

    /**
     * 获取歌曲详情
     * "https://www.kugou.com/yy/index.php?r=play/getdata&hash=$mid"
     */
    fun getTingSongInfo(music: Music): Observable<Music>? {
        return infoApiService.getTingSongInfo(music.mid + "")
                .flatMap {
                    Observable.create(ObservableOnSubscribe<Music> { e ->
                        try {
                            if (it.status == 1) {
                                val songInfo = it.data
                                songInfo.let {
                                    music.type = Constants.KUGOU
                                    music.isOnline = true
                                    music.mid = songInfo.hash
                                    music.album = songInfo.album_name
                                    music.albumId = songInfo.album_id
                                    music.artistId = songInfo.author_id
                                    music.artist = songInfo.author_name
                                    music.title = songInfo.song_name
                                    music.uri = songInfo.play_url
                                    music.fileSize = songInfo.filesize.toLong()
                                    music.lyric = ""
                                    music.coverSmall = MusicUtils.getAlbumPic(songInfo.img, Constants.KUGOU, MusicUtils.PIC_SIZE_SMALL)
                                    music.coverUri = MusicUtils.getAlbumPic(songInfo.img, Constants.KUGOU, MusicUtils.PIC_SIZE_NORMAL)
                                    music.coverBig = MusicUtils.getAlbumPic(songInfo.img, Constants.KUGOU, MusicUtils.PIC_SIZE_BIG)
                                }
                            }
                            if (music.uri != null) {
                                SongLoader.updateMusic(music)
                                e.onNext(music)
                                e.onComplete()
                            } else {
                                e.onError(Throwable())
                            }
                        } catch (error: Exception) {
                            e.onError(Throwable(error.message))
                        }
                        e.onNext(music)
                        e.onComplete()
                    })
                }
    }

}
