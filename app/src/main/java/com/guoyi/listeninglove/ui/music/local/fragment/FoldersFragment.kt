package com.guoyi.listeninglove.ui.music.local.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.FolderInfo
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.base.BaseLazyFragment
import com.guoyi.listeninglove.ui.music.dialog.BottomDialogFragment
import com.guoyi.listeninglove.ui.music.edit.EditSongListActivity
import com.guoyi.listeninglove.ui.music.local.adapter.FolderAdapter
import com.guoyi.listeninglove.ui.music.local.adapter.SongAdapter
import com.guoyi.listeninglove.ui.music.local.contract.FoldersContract
import com.guoyi.listeninglove.ui.music.local.presenter.FoldersPresenter
import kotlinx.android.synthetic.main.frag_local_song.*
import kotlinx.android.synthetic.main.header_local_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by D22434 on 2018/1/8.
 */

class FoldersFragment : BaseLazyFragment<FoldersPresenter>(), FoldersContract.View {

    private var mAdapter: FolderAdapter? = null
    private var mSongAdapter: SongAdapter? = null
    var folderInfos = mutableListOf<FolderInfo>()
    var songList = mutableListOf<Music>()
    var curFolderName: String? = null

    override fun getLayoutId(): Int {
        return R.layout.frag_local_song
    }

    override fun initViews() {
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        initHeader()
    }

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun listener() {
        menuIv.setOnClickListener {
            EditSongListActivity.musicList = songList
            startActivity<EditSongListActivity>()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onLazyLoad() {
        mPresenter?.loadFolders()
    }

    override fun showEmptyView() {
        mAdapter?.setEmptyView(R.layout.view_song_empty)
    }

    override fun showFolders(folderInfos: List<FolderInfo>) {
        updateHeader(true)
        if (mAdapter == null) {
            this.folderInfos = folderInfos as MutableList<FolderInfo>
            mAdapter = FolderAdapter(folderInfos)
            recyclerView?.adapter = mAdapter
            mAdapter?.bindToRecyclerView(recyclerView)
            mAdapter?.setOnItemClickListener { adapter, _, position ->
                val folderInfo = adapter.getItem(position) as FolderInfo?
                folderInfo?.folderPath?.let {
                    mPresenter?.loadSongs(it)
                    updateHeader(false, it)
                }
            }
        } else {
            recyclerView?.adapter = mAdapter
            mAdapter?.setNewData(folderInfos)
        }
    }


    override fun showSongs(musicList: MutableList<Music>?) {
        songList.clear()
        musicList?.let { songList = it }
        if (mSongAdapter == null) {
            mSongAdapter = musicList?.let {
                SongAdapter(it)
            }
            recyclerView?.adapter = mSongAdapter
            mSongAdapter?.bindToRecyclerView(recyclerView)
            mSongAdapter?.setOnItemClickListener { adapter, view, position ->
                if (view.id != R.id.iv_more) {
                    PlayManager.play(position, songList, Constants.PLAYLIST_DOWNLOAD_ID + curFolderName)
                    mSongAdapter?.notifyDataSetChanged()
                }
            }
            mSongAdapter?.setOnItemChildClickListener { _, _, position ->
                BottomDialogFragment.newInstance(songList[position]).apply {
                    removeSuccessListener = {
                        this@FoldersFragment.mAdapter?.notifyItemRemoved(position)
                    }
                }.show(mFragmentComponent.activity as AppCompatActivity)

            }
        } else {
            recyclerView?.adapter = mSongAdapter
            mSongAdapter?.setNewData(songList)
        }
    }

    private fun updateHeader(isFolderMode: Boolean, curFolder: String? = null) {
        swipe_refresh.isRefreshing = false
        if (isFolderMode) {
            songNumTv.text = "..."
            reloadIv.visibility = View.GONE
            menuIv.visibility = View.GONE
        } else {
            curFolderName = curFolder
            songNumTv.text = curFolder
            reloadIv.visibility = View.VISIBLE
            menuIv.visibility = View.VISIBLE
        }
    }

    /**
     * 初始化文件头
     */
    private fun initHeader() {
        songNumTv.text = "..."
        reloadIv.visibility = View.GONE
        menuIv.visibility = View.GONE
        iconIv.setImageResource(R.drawable.ic_folder)
        reloadIv.setImageResource(R.drawable.ic_arrow_back)
        reloadIv.setOnClickListener {
            reloadIv.visibility = View.GONE
            showFolders(folderInfos)
        }
    }

    companion object {

        fun newInstance(): FoldersFragment {

            val args = Bundle()

            val fragment = FoldersFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
