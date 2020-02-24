package com.guoyi.listeninglove.ui.music.edit

import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.ui.base.BaseActivity
import com.guoyi.listeninglove.ui.deleteLocalMusic
import com.guoyi.listeninglove.ui.downloadBatchMusic
import kotlinx.android.synthetic.main.activity_song_edit.*

/**
 * Des    : 歌曲批量操作
 * Author : master.
 * Date   : 2018/9/2 .
 */
class EditSongListActivity : BaseActivity<EditSongListPresenter>() {

    companion object {
        var musicList = mutableListOf<Music>()
    }

    var mAdapter: EditSongAdapter? = null

    override fun getLayoutResID(): Int {
        return R.layout.activity_song_edit
    }

    override fun setToolbarTitle(): String? {
        return getString(R.string.title_batch_operate)
    }

    override fun initView() {
        mAdapter = EditSongAdapter(musicList)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        mAdapter?.bindToRecyclerView(recyclerView)
    }

    override fun initData() {
        mAdapter?.setNewData(musicList)
        //是否支持批量删除
        musicList.forEach {
            if (it.type == Constants.LOCAL) {
                deleteTv.visibility = View.VISIBLE
                return@forEach
            }
        }
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun listener() {
        super.listener()
        playlistAddIv.setOnClickListener {
            val selectMusic = mutableListOf<Music>()
            mAdapter?.checkedMap?.forEach {
                selectMusic.add(it.value)
            }
            PlaylistManagerUtils.addToPlaylist(this, selectMusic)
        }
        downloadTv.setOnClickListener {
            val selectMusic = mutableListOf<Music>()
            mAdapter?.checkedMap?.forEach {
                if (it.value.isDl && it.value.type != Constants.LOCAL) {
                    selectMusic.add(it.value)
                }
            }
            downloadBatchMusic(selectMusic)
        }

        deleteTv.setOnClickListener {
            val selectMusic = mutableListOf<Music>()
            mAdapter?.checkedMap?.forEach {
                if (it.value.type == Constants.LOCAL) {
                    selectMusic.add(it.value)
                }
            }
            deleteLocalMusic(selectMusic) {
                musicList.removeAll(selectMusic)
                mAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_batch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_select_all) {
            if (mAdapter?.checkedMap?.size == musicList.size) {
                item.title = getString(R.string.all_select)
                mAdapter?.checkedMap?.clear()
            } else {
                item.title = getString(R.string.all_un_select)
                musicList.forEach {
                    mAdapter?.checkedMap?.put(it.mid.toString(), it)
                }
            }
            mAdapter?.notifyDataSetChanged()
        }
        return super.onOptionsItemSelected(item)
    }

}