package com.guoyi.listeninglove.ui.music.mv

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoyi.musicapi.netease.CommentsItemInfo
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.utils.CoverLoader
import com.guoyi.listeninglove.utils.FormatUtil

/**
 * 作者：yonglong on 2016/8/10 21:36
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class MvCommentAdapter(list: List<CommentsItemInfo>) : BaseQuickAdapter<CommentsItemInfo, BaseViewHolder>(R.layout.item_comment, list) {

    override fun convert(helper: BaseViewHolder, item: CommentsItemInfo) {
        helper.setText(R.id.tv_comment_user, item.user.nickname)
        helper.setText(R.id.tv_comment_time, getPublishTime(item.time))
        helper.setText(R.id.tv_comment_content, item.content)
        CoverLoader.loadImageView(mContext, item.user.avatarUrl, helper.getView<ImageView>(R.id.civ_cover))
    }

    private fun getPublishTime(time: Long): String {
        return FormatUtil.formatDate(time)
    }
}