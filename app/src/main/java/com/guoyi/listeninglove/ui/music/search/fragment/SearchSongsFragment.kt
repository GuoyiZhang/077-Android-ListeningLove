package com.guoyi.listeninglove.ui.music.search.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.HotSearchBean
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.SearchHistoryBean
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.base.BaseLazyFragment
import com.guoyi.listeninglove.ui.music.dialog.BottomDialogFragment
import com.guoyi.listeninglove.ui.music.local.adapter.SongAdapter
import com.guoyi.listeninglove.ui.music.search.SearchContract
import com.guoyi.listeninglove.ui.music.search.SearchEngine
import com.guoyi.listeninglove.ui.music.search.SearchPresenter
import com.guoyi.listeninglove.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_recyclerview_notoolbar.*

/**
 * 功能：本地歌曲列表
 * 作者：yonglong on 2016/8/10 20:49
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class SearchSongsFragment : BaseLazyFragment<SearchPresenter>(), SearchContract.View {

    private var mAdapter: SongAdapter? = null
    /**
     * 歌曲列表
     */
    private val musicList = mutableListOf<Music>()
    /**
     * 分页偏移量
     */
    private var mCurrentCounter = 0
    private val limit = 15
    private var mOffset = 0

    private var searchInfo: String = ""
    private var TAG = "SearchSongsFragment"
    private var type = SearchEngine.Filter.ANY


    //上拉加载更多监听事件
    val listener = BaseQuickAdapter.RequestLoadMoreListener {
        recyclerView.postDelayed({
            LogUtil.d(TAG, "mCurrentCounter=$mCurrentCounter")
            if (mCurrentCounter == 0) {
                //数据全部加载完毕
                mAdapter?.loadMoreEnd()
            } else {
                //成功获取更多数据
                mPresenter?.search(searchInfo, type, limit, mOffset)
            }
        }, 1000)
    }

    companion object {
        fun newInstance(searchInfo: String?, type: SearchEngine.Filter): SearchSongsFragment {
            val args = Bundle()
            val fragment = SearchSongsFragment()
            args.putString("searchInfo", searchInfo)
            args.putString("type", type.toString())
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recyclerview_notoolbar
    }

    override fun initViews() {
        searchInfo = arguments?.getString("searchInfo") ?: ""
        type = when (arguments?.getString("type")) {
            "KUGOU" -> SearchEngine.Filter.KUGOU
            "QQ" -> SearchEngine.Filter.QQ
            "BAIDU" -> SearchEngine.Filter.BAIDU
            "XIAMI" -> SearchEngine.Filter.XIAMI
            "NETEASE" -> SearchEngine.Filter.NETEASE
            else -> SearchEngine.Filter.ANY
        }
        LogUtil.d(TAG, "初始化 $type")
        musicList.clear()
    }


    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun listener() {
    }

    override fun onLazyLoad() {
        mPresenter?.search(searchInfo, type, limit, mOffset)
    }


    /**
     * 更新歌曲列表
     */
    fun updateMusicList(songList: MutableList<Music>) {
        musicList.addAll(songList)

        if (mAdapter == null) {
            mAdapter = SongAdapter(musicList)
            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
            recyclerView.adapter = mAdapter
            mAdapter?.bindToRecyclerView(recyclerView)
            mAdapter?.setOnLoadMoreListener(listener, recyclerView)

            mAdapter?.setOnItemClickListener { _, view, position ->
                if (musicList.size <= position) return@setOnItemClickListener
                PlayManager.playOnline(musicList[position])
            }
            mAdapter?.setOnItemChildClickListener { _, _, position ->
                val music = musicList[position]
                BottomDialogFragment.newInstance(music, Constants.PLAYLIST_SEARCH_ID).show(activity as AppCompatActivity)
            }

        } else {
            mAdapter?.setNewData(musicList)
        }
        hideLoading()
    }

    override fun showSearchResult(list: MutableList<Music>) {
        if (list.size != 0) {
            mOffset++
        } else {
            mAdapter?.loadMoreComplete()
            mAdapter?.setEnableLoadMore(false)
        }
        //更新歌曲列表
        updateMusicList(list)

        mCurrentCounter = list.size
        if (musicList.size == 0) {
            mAdapter?.loadMoreComplete()
            mAdapter?.setEnableLoadMore(false)
            showEmptyState()
        }
    }

    override fun showHotSearchInfo(list: MutableList<HotSearchBean>) {
    }


    override fun showSearchHistory(list: MutableList<SearchHistoryBean>) {
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
    }

}
