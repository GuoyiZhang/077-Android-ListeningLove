package com.guoyi.listeninglove.ui.music.importplaylist

import android.content.Intent
import android.view.View
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.api.music.MusicApiServiceImpl
import com.guoyi.listeninglove.api.net.ApiManager
import com.guoyi.listeninglove.api.net.RequestCallBack
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.bean.Playlist
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.common.NavigationHelper
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.music.edit.PlaylistManagerUtils
import com.guoyi.listeninglove.ui.base.BaseActivity
import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.ui.music.dialog.BottomDialogFragment
import com.guoyi.listeninglove.ui.music.local.adapter.SongAdapter
import com.guoyi.listeninglove.utils.LogUtil
import com.guoyi.listeninglove.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_import_playlist.*


@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class ImportPlaylistActivity : BaseActivity<BasePresenter<BaseContract.BaseView>>() {

    private val TAG = "ImportPlaylistActivity"
    var mAdapter: SongAdapter? = null
    var name: String? = null
    var vendor: String? = null
    var musicList = mutableListOf<Music>()

    override fun getLayoutResID(): Int {
        return R.layout.activity_import_playlist
    }

    override fun setToolbarTitle(): String {
        return getString(R.string.import_playlist)
    }

    override fun initView() {
    }

    override fun initData() {
        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            if (Constants.TEXT_PLAIN == intent.type) {
                val title = intent?.getStringExtra(Intent.EXTRA_TEXT)
                playlistInputView.editText?.setText(title)
            }
        }
    }

    override fun initInjector() {
    }

    override fun listener() {
        super.listener()
        //同步歌单
        syncBtn.setOnClickListener {
            showLoading(true)
            val playlistLink = playlistInputView.editText?.text.toString()
            getPlaylistId(playlistLink)
        }
        //导入歌单
        importBtn.setOnClickListener {
            if (name == null) {
                ToastUtils.show("请先同步获取歌曲！")
                return@setOnClickListener
            }
            if (vendor == null) {
                ToastUtils.show("请先同步获取歌曲！")
                return@setOnClickListener
            }
            if (musicList.size == 0) return@setOnClickListener
            PlaylistManagerUtils.addToPlaylist(this,musicList)
        }
    }

    private fun getPlaylistId(link: String) {
        try {
            when {
                link.contains("music.163.com") -> {
                    val len = link.lastIndexOf("playlist/") + "playlist/".length
                    val id = link.substring(len, len + link.substring(len).indexOf("/"))
                    importMusic(Constants.NETEASE, id)
                }
                link.contains("y.qq.com") -> {
                    val len = link.lastIndexOf("id=") + "id=".length

                    val idString = link.substring(len)
                    val id = if (idString.indexOf("&") > 0) {
                        link.substring(len, len + link.substring(len).indexOf("&")).trim()
                    } else {
                        idString
                    }
                    importMusic(Constants.QQ, id)
                }
                link.contains("www.xiami.com") -> {
                    val len = link.lastIndexOf("collect/") + "collect/".length
                    val end = if (link.indexOf("?") == -1) link.indexOf("(") else link.indexOf("?")
                    val id = link.substring(len, end).trim()
                    importMusic(Constants.XIAMI, id)
                }
                link.contains("h.xiami.com") -> {
                    val start = link.lastIndexOf("id=") + "id=".length
                    val end = link.substring(start).indexOf(" ")
                    val id = link.substring(start, start + end).trim()
                    importMusic(Constants.XIAMI, id)
                }
                else -> {
                    showLoading(false)
                    ToastUtils.show("请输入有效的链接！")
                }
            }
        } catch (e: Throwable) {
            showLoading(false)
            ToastUtils.show("请输入有效的链接！")
        }
    }

    private fun importMusic(vendor: String, pid: String) {
        this.vendor = vendor
        LogUtil.d(TAG, "正在导入歌单 $vendor $pid")
        val observable = MusicApiServiceImpl.getPlaylistSongs(vendor, pid)
        ApiManager.request(observable, object : RequestCallBack<Playlist> {
            override fun success(result: Playlist) {
                showLoading(false)
                musicList.clear()
                musicList = result.musicList
                showResultAdapter(result)
            }

            override fun error(msg: String) {
                showLoading(false)
                ToastUtils.show(msg)
            }
        })
    }

    fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    /**
     * 显示结果
     */
    fun showResultAdapter(result: Playlist) {
        mAdapter = SongAdapter(result.musicList)
        this.name = result.name
        resultRsv.adapter = mAdapter
        resultRsv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        mAdapter?.bindToRecyclerView(resultRsv)

        mAdapter?.setOnItemClickListener { adapter, view, position ->
            if (view.id != R.id.iv_more) {
                PlayManager.play(position, result.musicList, Constants.PLAYLIST_DOWNLOAD_ID + result.pid)
                mAdapter?.notifyDataSetChanged()
            }
        }
        mAdapter?.setOnItemChildClickListener { _, _, position ->
            BottomDialogFragment.newInstance(result.musicList[position], Constants.PLAYLIST_IMPORT_ID).show(this@ImportPlaylistActivity)
        }

    }

}
