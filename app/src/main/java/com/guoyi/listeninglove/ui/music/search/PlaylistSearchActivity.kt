package com.guoyi.listeninglove.ui.music.search

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.EditorInfo
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.base.BaseActivity
import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.ui.music.dialog.BottomDialogFragment
import com.guoyi.listeninglove.ui.music.local.adapter.SongAdapter
import com.guoyi.listeninglove.utils.Tools
import kotlinx.android.synthetic.main.activity_playlist_search.*
import kotlinx.android.synthetic.main.toolbar_search_layout.*

/**
 * 作者：yonglong on 2016/9/15 12:32
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
class PlaylistSearchActivity : BaseActivity<BasePresenter<BaseContract.BaseView>>() {
    /**
     * 搜索信息
     */
    private var queryString: String = ""
    /**
     * 搜索结果
     */
    private val searchResults = mutableListOf<Music>()

    companion object {
        /**
         * 歌曲列表
         */
        var musicList = mutableListOf<Music>()
    }

    /**
     * 适配器
     */
    private var mAdapter: SongAdapter = SongAdapter(searchResults)


    override fun getLayoutResID(): Int {
        return R.layout.activity_playlist_search
    }

    override fun initView() {
        showSearchOnStart()
    }

    private fun showSearchOnStart() {
        searchEditText.setText(queryString)
        searchEditText.setHint(R.string.search_playlist)
        if (TextUtils.isEmpty(queryString) || TextUtils.isEmpty(searchEditText.text)) {
            searchToolbarContainer.translationX = 100f
            searchToolbarContainer.alpha = 0f
            searchToolbarContainer.visibility = View.VISIBLE
            searchToolbarContainer.animate().translationX(0f).alpha(1f).setDuration(200).setInterpolator(DecelerateInterpolator()).start()
        } else {
            searchToolbarContainer.translationX = 0f
            searchToolbarContainer.alpha = 1f
            searchToolbarContainer.visibility = View.VISIBLE
        }
    }

    override fun initData() {
        mAdapter.setEnableLoadMore(true)
        //初始化列表
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        resultListRcv.layoutManager = layoutManager
        resultListRcv.adapter = mAdapter
        mAdapter.bindToRecyclerView(resultListRcv)

    }

    override fun initInjector() {
    }

    /**
     * 监听事件
     */
    override fun listener() {
        clearSearchIv.setOnClickListener {
            queryString = ""
            searchEditText.setText("")
            clearSearchIv.visibility = View.GONE
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                val newText = searchEditText.text.toString()
                clearSearchIv.visibility = View.VISIBLE
                searchLocal(newText)
                if (newText.isEmpty()) {
                    searchEditText.setHint(R.string.search_playlist)
                }
            }
        })

        searchEditText.setOnEditorActionListener { _, _, event ->
            if (event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER || event.action == EditorInfo.IME_ACTION_SEARCH)) {
                searchLocal(queryString)
                return@setOnEditorActionListener true
            }
            false
        }

        mAdapter.setOnItemClickListener { _, view, position ->
            if (musicList.size <= position) return@setOnItemClickListener
            //播放歌单内搜索结果队列
            PlayManager.play(position,searchResults,queryString.hashCode().toString())
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            val music = musicList[position]
            BottomDialogFragment.newInstance(music, Constants.PLAYLIST_SEARCH_ID).show(this)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    /**
     * 本地歌单搜索索
     *
     * @param query
     */
    private fun searchLocal(query: String?) {
        searchResults.clear()
        if (query != null && query.isNotEmpty()) {
            queryString = query.toLowerCase()
            musicList.forEach {
                if (it.title?.toLowerCase()?.contains(queryString) == true ||
                        it.artist?.toLowerCase()?.contains(queryString) == true ||
                        it.album?.toLowerCase()?.contains(queryString) == true
                ) {
                    searchResults.add(it)
                }
            }
        }
        mAdapter.setNewData(searchResults)
    }

    override fun onResume() {
        super.onResume()
        Tools.hideInputView(searchEditText)
    }
}
