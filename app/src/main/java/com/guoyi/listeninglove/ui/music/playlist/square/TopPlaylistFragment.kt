package com.guoyi.listeninglove.ui.music.playlist.square

import android.os.Bundle
import android.util.Pair
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.common.NavigationHelper
import com.guoyi.listeninglove.ui.base.BaseFragment
import com.guoyi.listeninglove.ui.music.charts.PlaylistContract
import com.guoyi.listeninglove.ui.music.charts.PlaylistPresenter
import com.guoyi.listeninglove.ui.music.discover.TopPlaylistAdapter
import kotlinx.android.synthetic.main.frag_top_playlist.*

/**
 * Created by Monkey on 2015/6/29.
 * 精品歌单列表
 */
class TopPlaylistFragment : BaseFragment<PlaylistPresenter>(), PlaylistContract.View {

    /**
     * 适配器
     */
    private var mNeteaseAdapter: TopPlaylistAdapter? = null
    private var mTag: String = "全部"

    /**
     * 数据集合
     */
    private var playlist = mutableListOf<Playlist>()

    override fun showNeteaseCharts(playlistList: MutableList<Playlist>) {
        this.playlist = playlistList
        if (mNeteaseAdapter == null) {
            //适配器
            mNeteaseAdapter = TopPlaylistAdapter(playlist)
            playlistRcv?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 3, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            playlistRcv?.adapter = mNeteaseAdapter
            playlistRcv?.isFocusable = false
            playlistRcv?.isNestedScrollingEnabled = false
            mNeteaseAdapter?.bindToRecyclerView(playlistRcv)
            mNeteaseAdapter?.setOnItemClickListener { adapter, view, position ->
                val playlist = adapter.data[position] as Playlist
                NavigationHelper.navigateToPlaylist(mFragmentComponent.activity, playlist, Pair(view.findViewById(R.id.iv_cover), getString(R.string.transition_album)))
            }
        } else {
            mNeteaseAdapter?.setNewData(playlist)
        }
    }


    override fun showPlayList(playlist: Playlist?) {
    }

    override fun showOnlineMusicList(musicList: MutableList<Music>?) {

    }


    override fun getLayoutId(): Int {
        return R.layout.frag_top_playlist
    }

    public override fun initViews() {
        cateTagTv.text = mTag
        cateTagFilterTv.setOnClickListener {
            val allCategoryFragment = TopPlaylistCatFragment()
            allCategoryFragment.isHighQuality = true
            allCategoryFragment.successListener = {
                updateTag(it)
            }
            allCategoryFragment.showIt(context as androidx.fragment.app.FragmentActivity)
        }
    }

    /**
     * 更新Tag分类
     */
    private fun updateTag(newTag: String) {
        mTag = newTag
        cateTagTv.text = mTag
        mPresenter?.loadHighQualityPlaylist(mTag)
    }

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun loadData() {
        mPresenter?.loadHighQualityPlaylist(mTag)
    }


    override fun listener() {

    }

    companion object {
        fun newInstance(): TopPlaylistFragment {
            val args = Bundle()
            val fragment = TopPlaylistFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
