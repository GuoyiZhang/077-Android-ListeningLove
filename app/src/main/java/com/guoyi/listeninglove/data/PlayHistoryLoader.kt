package com.guoyi.listeninglove.data

import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.data.db.DaoLitepal
import com.guoyi.listeninglove.bean.Music

/**
 * 作者：yonglong on 2016/11/4 22:30
 */
object PlayHistoryLoader {

    private val TAG = "PlayQueueLoader"

    /**
     * 添加歌曲到播放历史
     */
    fun addSongToHistory(music: Music) {
        try {
            DaoLitepal.addToPlaylist(music, Constants.PLAYLIST_HISTORY_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 获取播放历史
     */
    fun getPlayHistory(): MutableList<Music> {
        return DaoLitepal.getMusicList(Constants.PLAYLIST_HISTORY_ID, "updateDate desc")
    }

    /**
     * 清除播放历史
     */
    fun clearPlayHistory() {
        try {
            DaoLitepal.clearPlaylist(Constants.PLAYLIST_HISTORY_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
