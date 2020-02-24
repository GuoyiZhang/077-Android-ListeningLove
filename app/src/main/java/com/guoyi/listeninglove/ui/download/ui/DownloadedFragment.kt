package com.guoyi.listeninglove.ui.download.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.ui.base.BaseFragment
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.ui.download.TasksManagerModel
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.music.dialog.BottomDialogFragment
import com.guoyi.listeninglove.ui.music.local.adapter.SongAdapter

import java.util.ArrayList

import com.guoyi.listeninglove.event.PlaylistEvent
import kotlinx.android.synthetic.main.fragment_recyclerview_notoolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by yonglong on 2016/11/26.
 */

class DownloadedFragment : BaseFragment<DownloadPresenter>(), DownloadContract.View {

    private var mAdapter: SongAdapter? = null
    private var isCache: Boolean? = null
    private var musicList: List<Music> = ArrayList()

    override fun listener() {
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            if (view.id != R.id.iv_more) {
                PlayManager.play(position, musicList, Constants.PLAYLIST_DOWNLOAD_ID)
                mAdapter?.notifyDataSetChanged()
            }
        }
        mAdapter?.setOnItemChildClickListener { adapter, _, position ->
            val music = adapter.getItem(position) as Music?
            BottomDialogFragment.newInstance(music, Constants.PLAYLIST_DOWNLOAD_ID).apply {
                removeSuccessListener = {
                    EventBus.getDefault().post(PlaylistEvent(Constants.PLAYLIST_DOWNLOAD_ID))
                    this@DownloadedFragment.mAdapter?.notifyItemRemoved(position)
                }
            }.show(mFragmentComponent.activity as AppCompatActivity)
        }
    }

    override fun loadData() {
        isCache = arguments?.getBoolean(Constants.KEY_IS_CACHE)
        mPresenter?.loadDownloadMusic(isCache!!)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recyclerview_notoolbar
    }

    public override fun initViews() {
        mAdapter = SongAdapter(musicList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recyclerView)
    }

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }


    override fun showErrorInfo(msg: String) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_download, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete_all) {
            if (mPresenter != null) {
                mPresenter?.deleteAll()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showSongs(musicList: List<Music>) {
        this.musicList = musicList
        mAdapter?.setNewData(musicList)
        if (musicList.isEmpty()) {
            mAdapter?.setEmptyView(R.layout.view_song_empty, recyclerView)
        }
    }

    override fun showDownloadList(modelList: List<TasksManagerModel>) {

    }

    companion object {

        fun newInstance(isCache: Boolean?): DownloadedFragment {
            val args = Bundle()
            args.putBoolean(Constants.KEY_IS_CACHE, isCache!!)
            val fragment = DownloadedFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
