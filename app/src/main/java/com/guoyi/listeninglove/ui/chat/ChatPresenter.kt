package com.guoyi.listeninglove.ui.chat

import com.guoyi.listeninglove.MusicApp
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.api.music.MusicUtils
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.socket.SocketManager
import com.guoyi.listeninglove.utils.FormatUtil
import com.guoyi.listeninglove.utils.ToastUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Des    :
 * Author : master.
 * Date   : 2018/9/27 .
 */
class ChatPresenter @Inject
constructor() : BasePresenter<ChatContract.View>(), ChatContract.Presenter {

    val model by lazy { ChatModel() }
    override fun deleteMessages() {
        doAsync {
            val data = model.deleteAllMessages()
            uiThread {
                mView?.deleteSuccessful()
            }
        }
    }

    /**
     * 加载云消息,默认加载云消息
     */
    override fun loadMessages(end: String?) {
        var endTime: String? = null
        val start = end?.let {
            val time = FormatUtil.getChatParseDateTime(it)
            endTime = FormatUtil.getChatDateTime(time - 1)
            FormatUtil.getChatDateTime(time - 1000 * 60 * 60 * 24 * 2)
        }
        model.getChatHistory(start, endTime, success = {
            it?.let { it1 -> mView?.showHistortMessages(it1) }
        }, fail = {
            mView?.hideLoading()
            ToastUtils.show(it)
        })
    }

    /**
     * 加载本地消息
     */
    override fun loadLocalMessages() {
        doAsync {
            val data = model.loadHistoryMessages()
            uiThread {
                mView?.showMessages(data)
            }
        }
    }

    /**
     * 发送正在播放的音乐
     */
    override fun sendMusicMessage() {
        val music = PlayManager.getPlayingMusic()
        when {
            music?.type == Constants.LOCAL -> {
                val message = MusicApp.getAppContext().getString(R.string.share_local_song, music.artist, music.title)
                SocketManager.sendSocketMessage(message, SocketManager.MESSAGE_BROADCAST)
            }
            music?.mid != null -> {
                val message = MusicApp.GSON.toJson(MusicUtils.getMusicInfo(music))
                SocketManager.sendSocketMessage(message, SocketManager.MESSAGE_SHARE)
            }
            else -> ToastUtils.show(MusicApp.getAppContext().getString(R.string.playing_empty))
        }
    }

}