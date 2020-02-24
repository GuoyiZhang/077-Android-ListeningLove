package com.guoyi.listeninglove.ui.music.search


import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.bean.HotSearchBean
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.SearchHistoryBean

interface SearchContract {

    interface View : BaseContract.BaseView {

        fun showSearchResult(list: MutableList<Music>)
        fun showHotSearchInfo(list: MutableList<HotSearchBean>)
        fun showSearchHistory(list: MutableList<SearchHistoryBean>)
    }

    interface Presenter : BaseContract.BasePresenter<View> {

        fun search(key: String, filter: SearchEngine.Filter, limit: Int, page: Int)

        fun searchByType(key: String, offset: Int, type: Int)

        fun searchLocal(key: String)

        fun getHotSearchInfo()

        fun saveQueryInfo(query: String)

        fun getSearchHistory()

    }
}
