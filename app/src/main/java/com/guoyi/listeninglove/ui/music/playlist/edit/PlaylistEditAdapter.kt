package com.guoyi.listeninglove.ui.music.playlist.edit

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.utils.ConvertUtils

/**
 * 作者：yonglong
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class PlaylistEditAdapter(list: MutableList<Playlist>) : BaseItemDraggableAdapter<Playlist, BaseViewHolder>(R.layout.item_playlist_edit, list) {
    /**
     * 选中列表
     */
    var checkedMap = mutableMapOf<String, Playlist>()

    override fun convert(holder: BaseViewHolder, item: Playlist) {
        holder.setText(R.id.tv_name, ConvertUtils.getTitle(item.name))
        holder.getView<CheckBox>(R.id.cb_playlist).isChecked = checkedMap.containsKey(item.pid.toString())

        holder.itemView.setOnClickListener {
            if (checkedMap.containsKey(item.pid.toString())) {
                checkedMap.remove(item.pid.toString())
            } else {
                checkedMap[item.pid.toString()] = item
            }
            notifyItemChanged(holder.adapterPosition)
        }
    }
}