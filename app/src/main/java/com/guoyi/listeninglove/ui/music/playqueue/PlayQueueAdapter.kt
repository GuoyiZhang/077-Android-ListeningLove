package com.guoyi.listeninglove.ui.music.playqueue

import androidx.core.content.ContextCompat
import android.view.View
import android.widget.TextView

import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.api.music.MusicApi
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.theme.ThemeStore
import com.guoyi.listeninglove.utils.ConvertUtils
import com.guoyi.listeninglove.utils.CoverLoader
import com.guoyi.listeninglove.utils.ToastUtils
import org.jetbrains.anko.dip

/**
 * Created by D22434 on 2017/9/26.ß
 */

class PlayQueueAdapter(musicList: List<Music>) : BaseItemDraggableAdapter<Music, BaseViewHolder>(R.layout.item_play_queue, musicList) {

    override fun convert(holder: BaseViewHolder, item: Music) {
        CoverLoader.loadImageView(mContext, item.coverUri, holder.getView(R.id.iv_cover))
        holder.setText(R.id.tv_title, ConvertUtils.getTitle(item.title))

        //音质图标显示
        val quality = when {
            item.sq -> mContext.resources.getDrawable(R.drawable.sq_icon, null)
            item.hq -> mContext.resources.getDrawable(R.drawable.hq_icon, null)
            else -> null
        }
        quality?.let {
            quality.setBounds(0, 0, quality.minimumWidth + mContext.dip(2), quality.minimumHeight)
            holder.getView<TextView>(R.id.tv_artist).setCompoundDrawables(quality, null, null, null)
        }
        //设置歌手专辑名
        holder.setText(R.id.tv_artist, ConvertUtils.getArtistAndAlbum(item.artist, item.album))
        //设置播放状态
        if (PlayManager.getPlayingId() == item.mid) {
            holder.getView<View>(R.id.v_playing).visibility = View.VISIBLE
            holder.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.app_green))
            holder.setTextColor(R.id.tv_artist, ContextCompat.getColor(mContext, R.color.app_green))
        } else {
            if (ThemeStore.THEME_MODE == ThemeStore.DAY) {
                holder.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.black))
            } else {
                holder.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.white))
            }
            holder.getView<View>(R.id.v_playing).visibility = View.GONE
            holder.setTextColor(R.id.tv_artist, ContextCompat.getColor(mContext, R.color.grey))
        }
        holder.addOnClickListener(R.id.iv_more)

        if (item.isCp) {
            holder.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.grey))
            holder.setTextColor(R.id.tv_artist, ContextCompat.getColor(mContext, R.color.grey))
        }

        if (item.type == Constants.LOCAL) {
            holder.getView<View>(R.id.iv_resource).visibility = View.GONE
        } else {
            holder.getView<View>(R.id.iv_resource).visibility = View.VISIBLE
            when {
                item.type == Constants.BAIDU -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.baidu)
                }
                item.type == Constants.NETEASE -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.netease)
                }
                item.type == Constants.QQ -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.qq)
                }
                item.type == Constants.XIAMI -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.xiami)
                }
            }
        }
        if (item.coverUri != null) {
            CoverLoader.loadImageView(mContext, item.coverUri, holder.getView(R.id.iv_cover))
        }
        if (item.coverUri.isNullOrEmpty()) {
            //加载歌曲专辑图
            item.title?.let {
                MusicApi.getMusicAlbumPic(item.title.toString(), success = {
                    item.coverUri = it
                    CoverLoader.loadImageView(mContext, it, holder.getView(R.id.iv_cover))
                })
            }
        }
        if (item.isCp) {
            holder.itemView.setOnClickListener {
                ToastUtils.show("歌曲无法播放")
            }
        }
        holder.addOnClickListener(R.id.iv_more)
        holder.addOnClickListener(R.id.iv_love)
    }
}
