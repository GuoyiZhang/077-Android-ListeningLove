package com.guoyi.listeninglove.ui.chat

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoyi.musicapi.playlist.MusicInfo
import com.guoyi.listeninglove.MusicApp
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.api.music.MusicUtils
import com.guoyi.listeninglove.bean.MessageInfoBean
import com.guoyi.listeninglove.bean.UserInfoBean
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.socket.SocketManager
import com.guoyi.listeninglove.ui.music.dialog.BottomDialogFragment
import com.guoyi.listeninglove.utils.ConvertUtils
import com.guoyi.listeninglove.utils.CoverLoader

/**
 * 作者：yonglong on 2016/8/10 21:36
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class ChatListAdapter(val list: List<MessageInfoBean>) : BaseQuickAdapter<MessageInfoBean, BaseViewHolder>(R.layout.item_chat, list) {

    override fun convert(holder: BaseViewHolder, item: MessageInfoBean) {
        holder.setText(R.id.tv_comment_user, item.userInfo?.nickname ?: "")

        if (holder.adapterPosition > 0 && item.datetime.length >= 19) {
            if (item.datetime.substring(0, 16) == list[holder.adapterPosition - 1].datetime.substring(0, 16)) {
                holder.getView<View>(R.id.tv_comment_time).visibility = View.GONE
            } else {
                holder.getView<View>(R.id.tv_comment_time).visibility = View.VISIBLE
                holder.setText(R.id.tv_comment_time, item.datetime)
            }
        }

        holder.setText(R.id.tv_comment_content, item.message)
        CoverLoader.loadImageView(mContext, item.userInfo?.avatar, holder.getView(R.id.civ_cover))
        if (item.type == SocketManager.MESSAGE_BROADCAST) {
            holder.getView<View>(R.id.tv_comment_content).visibility = View.VISIBLE
            holder.getView<View>(R.id.include_music).visibility = View.GONE
        } else if (item.type == SocketManager.MESSAGE_SHARE) {
            var musicInfo: MusicInfo? = null
            try {
                musicInfo = MusicApp.GSON.fromJson(item.message, MusicInfo::class.java)
            } catch (e: Throwable) {
            } finally {
                if (musicInfo != null) {
                    holder.getView<View>(R.id.include_music).visibility = View.VISIBLE
                    holder.getView<View>(R.id.tv_comment_content).visibility = View.GONE
                    val music = MusicUtils.getMusic(musicInfo)
                    holder.setText(R.id.tv_title, ConvertUtils.getTitle(music.title))
                    holder.setText(R.id.tv_artist, ConvertUtils.getArtistAndAlbum(music.artist, music.album))
                    if (music.type == Constants.LOCAL) {
                        holder.getView<View>(R.id.iv_resource).visibility = View.GONE
                    } else {
                        holder.getView<View>(R.id.iv_resource).visibility = View.VISIBLE
                        when (music.type) {
                            Constants.BAIDU -> {
                                holder.setImageResource(R.id.iv_resource, R.drawable.baidu)
                            }
                            Constants.NETEASE -> {
                                holder.setImageResource(R.id.iv_resource, R.drawable.netease)
                            }
                            Constants.QQ -> {
                                holder.setImageResource(R.id.iv_resource, R.drawable.qq)
                            }
                            Constants.XIAMI -> {
                                holder.setImageResource(R.id.iv_resource, R.drawable.xiami)
                            }
                        }
                    }
                    if (music.coverUri != null) {
                        CoverLoader.loadImageView(mContext, music.coverUri, holder.getView(R.id.iv_cover))
                    }
                    holder.getView<View>(R.id.include_music).setOnClickListener {
                        PlayManager.playOnline(music)
                    }
                    holder.getView<View>(R.id.iv_more).setOnClickListener {
                        BottomDialogFragment.newInstance(music).show(mContext as ChatActivity)
                    }
                }
            }
        }
    }

}

class OnlineUserListAdapter(list: List<UserInfoBean>) : BaseQuickAdapter<UserInfoBean, BaseViewHolder>(R.layout.item_user, list) {
    override fun convert(helper: BaseViewHolder, item: UserInfoBean) {
        CoverLoader.loadImageView(mContext, item.avatar, helper.getView(R.id.user_avatar))
        helper.setText(R.id.user_name, item.nickname)
        helper.setText(R.id.user_name, item.nickname)
        when {
            item.platform == "android" -> helper.setImageResource(R.id.platformIv, R.drawable.ic_phone_android)
            item.platform == "linux" -> helper.setImageResource(R.id.platformIv, R.drawable.linux)
            item.platform == "windows" -> helper.setImageResource(R.id.platformIv, R.drawable.ic_desktop_windows)
            item.platform == "pc" -> helper.setImageResource(R.id.platformIv, R.drawable.ic_desktop_windows)
            item.platform == "osx" -> helper.setImageResource(R.id.platformIv, R.drawable.ic_desktop_mac)
            else -> helper.setImageResource(R.id.platformIv, R.drawable.ic_phone_android)
        }
        helper.itemView.setOnClickListener {
        }
    }

}
