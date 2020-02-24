package com.guoyi.listeninglove.ui.music.local.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.FolderInfo

/**
 * Created by D22434 on 2018/1/11.
 */

class FolderAdapter(folderInfos: List<FolderInfo>) : BaseQuickAdapter<FolderInfo, BaseViewHolder>(R.layout.item_folder, folderInfos) {

    override fun convert(holder: BaseViewHolder, folderInfo: FolderInfo) {
        holder.setText(R.id.tv_title, folderInfo.folderName)
        holder.setText(R.id.tv_artist, folderInfo.folderPath)
    }
}