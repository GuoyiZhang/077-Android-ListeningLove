package com.guoyi.listeninglove.ui.music.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.HotSearchBean
import com.guoyi.listeninglove.bean.SearchHistoryBean


/**
 * 作者：yonglong on 2016/9/26 23:47
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class SearchHistoryAdapter(suggestions: List<SearchHistoryBean>) : BaseQuickAdapter<SearchHistoryBean, BaseViewHolder>(R.layout.item_search_suggestion, suggestions) {

    override fun convert(holder: BaseViewHolder, item: SearchHistoryBean) {
        holder.setText(R.id.item_suggestion_query, item.title)
        holder.addOnClickListener(R.id.deleteView)
        holder.addOnClickListener(R.id.history_search)
    }
}

class HotSearchAdapter(hotsearchInfo: List<HotSearchBean>) : BaseQuickAdapter<HotSearchBean, BaseViewHolder>(R.layout.item_search_hot, hotsearchInfo) {

    override fun convert(holder: BaseViewHolder, item: HotSearchBean) {
        holder.setText(R.id.titleTv, item.title)
        holder.addOnClickListener(R.id.titleTv)
    }
}
