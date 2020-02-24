package com.guoyi.listeninglove.ui.music.local.fragment

import android.os.Bundle

import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.ui.base.BaseLazyFragment
import com.guoyi.listeninglove.bean.Album
import com.guoyi.listeninglove.ui.music.local.adapter.AlbumAdapter
import com.guoyi.listeninglove.ui.music.local.contract.AlbumsContract
import com.guoyi.listeninglove.ui.music.local.presenter.AlbumPresenter

import java.util.ArrayList

import com.guoyi.listeninglove.event.FileEvent
import kotlinx.android.synthetic.main.fragment_recyclerview_notoolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 功能：本地歌曲列表
 * 作者：yonglong on 2016/8/10 20:49
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class AlbumFragment : BaseLazyFragment<AlbumPresenter>(), AlbumsContract.View {
    private var mAdapter: AlbumAdapter? = null
    private val albumList = ArrayList<Album>()

    /**
     * 初始化视图
     *
     * @return
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_recyclerview_notoolbar
    }

    /**
     * 初始化控件
     */
    override fun initViews() {
        mAdapter = AlbumAdapter(albumList)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recyclerView)
    }

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun listener() {}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onLazyLoad() {
        mPresenter?.loadAlbums("all")
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
    }

    override fun showAlbums(albumList: List<Album>) {
        mAdapter?.setNewData(albumList)
        if (albumList.isEmpty()) {
            mAdapter?.setEmptyView(R.layout.view_song_empty, recyclerView)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateDownloadEvent(event: FileEvent) {
        mPresenter?.loadAlbums("all")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    companion object {

        fun newInstance(): AlbumFragment {

            val args = Bundle()
            val fragment = AlbumFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
