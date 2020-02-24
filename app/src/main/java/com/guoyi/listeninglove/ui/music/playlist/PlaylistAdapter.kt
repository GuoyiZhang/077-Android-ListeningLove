package com.guoyi.listeninglove.ui.music.playlist

import androidx.appcompat.app.AppCompatActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.common.NavigationHelper
import com.guoyi.listeninglove.utils.CoverLoader

/**
 * 作者：yonglong on 2016/8/10 21:36
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class PlaylistAdapter(playlists: List<Playlist>) : BaseQuickAdapter<Playlist, BaseViewHolder>(R.layout.item_playlist, playlists) {

    override fun convert(helper: BaseViewHolder, playlist: Playlist) {
        helper.setText(R.id.tv_name, playlist.name)
        CoverLoader.loadImageView(mContext, playlist.coverUrl, helper.getView(R.id.iv_cover))
        helper.setText(R.id.tv_num, "${playlist.total}首")
        helper.setVisible(R.id.tv_num, true)

        helper.itemView.setOnClickListener {
            NavigationHelper.navigateToPlaylist(mContext as AppCompatActivity, playlist, null)
        }
    }
}

