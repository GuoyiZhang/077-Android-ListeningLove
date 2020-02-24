package com.guoyi.listeninglove.ui.music.bottom

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.common.Constants
import com.guoyi.listeninglove.common.NavigationHelper
import com.guoyi.listeninglove.event.MetaChangedEvent
import com.guoyi.listeninglove.event.PlayModeEvent
import com.guoyi.listeninglove.event.PlaylistEvent
import com.guoyi.listeninglove.event.StatusChangedEvent
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.base.BaseFragment
import com.guoyi.listeninglove.ui.music.playpage.PlayContract
import com.guoyi.listeninglove.ui.music.playpage.PlayPresenter
import com.guoyi.listeninglove.ui.music.playqueue.PlayQueueDialog
import com.guoyi.listeninglove.utils.LogUtil
import kotlinx.android.synthetic.main.play_control_menu.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class PlayControlFragment : BaseFragment<PlayPresenter>(), SeekBar.OnSeekBarChangeListener, PlayContract.View {
    override fun setPlayingBg(albumArt: Drawable?, isInit: Boolean?) {

    }

    private var coverAnimator: ObjectAnimator? = null

    private var mAdapter: BottomMusicAdapter? = null
    private val musicList = ArrayList<Music>()

    override fun getLayoutId(): Int {
        return R.layout.play_control_menu
    }

    public override fun initViews() {
        //初始化控件
        updatePlayStatus(PlayManager.isPlaying())
        musicList.clear()
        musicList.addAll(PlayManager.getPlayList())
        initSongList()
    }

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun listener() {
        bottomPlayRcv.setOnClickListener { v -> NavigationHelper.navigateToPlaying(mFragmentComponent.activity) }
        playQueueIv.setOnClickListener {
            PlayQueueDialog.newInstance().showIt((activity as AppCompatActivity))
        }
        playPauseView.setOnClickListener {
            PlayManager.playPause()
        }
    }

    override fun loadData() {
        val music = PlayManager.getPlayingMusic()
        mPresenter?.updateNowPlaying(music, true)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        if (PlayManager.isPlaying() || PlayManager.isPause()) {
            val progress = seekBar.progress
            PlayManager.seekTo(progress)
        } else {
            seekBar.progress = 0
        }
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }


    override fun setPlayingBitmap(albumArt: Bitmap?) {
    }

    override fun updatePlayStatus(isPlaying: Boolean) {
        if (isPlaying && !playPauseView.isPlaying) {
            playPauseView.play()
        } else if (!isPlaying && playPauseView.isPlaying) {
            playPauseView.pause()
        }
    }

    override fun updatePlayMode() {

    }

    override fun updateProgress(progress: Long, max: Long) {
        progressBar.progress = progress.toInt()
        progressBar.max = max.toInt()
        LogUtil.d(TAG, "progress : " + 1.0f * progress / max);
        playPauseView.setProgress(1.0f * progress / max)
    }


    override fun showNowPlaying(music: Music?) {
        if (music != null) {
            rootView.visibility = View.VISIBLE
        } else {
            rootView.visibility = View.GONE
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlayModeChangedEvent(event: PlayModeEvent) {
        updatePlayMode()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMetaChangedEvent(event: MetaChangedEvent) {
        mPresenter?.updateNowPlaying(event.music, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(PlayManager.getCurrentPosition(), true)
        } else {
            progressBar.progress = PlayManager.getCurrentPosition()
        }
        progressBar.max = PlayManager.getDuration()
        bottomPlayRcv.scrollToPosition(PlayManager.position())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onStatusChangedEvent(event: StatusChangedEvent) {
        playPauseView.setLoading(!event.isPrepared)
        updatePlayStatus(event.isPlaying)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlayListChange(event: PlaylistEvent) {
        if (event.type == Constants.PLAYLIST_QUEUE_ID) {
            LogUtil.d(TAG, "播放列表已改变")
            musicList.clear()
            musicList.addAll(PlayManager.getPlayList())
            mAdapter?.notifyDataSetChanged()
            bottomPlayRcv.scrollToPosition(PlayManager.position())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        coverAnimator = null
        EventBus.getDefault().unregister(this)
    }

    /**
     * 初始化歌曲列表
     */
    private fun initSongList() {
        if (mAdapter == null) {
            bottomPlayRcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mAdapter = BottomMusicAdapter(musicList)
            val snap = PagerSnapHelper()
            snap.attachToRecyclerView(bottomPlayRcv)
            bottomPlayRcv.adapter = mAdapter
            bottomPlayRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val manager = recyclerView?.layoutManager as LinearLayoutManager
                        val first = manager.findFirstVisibleItemPosition()
                        val last = manager.findLastVisibleItemPosition()
                        LogUtil.e("Scroll", "$first-$last")
                        if (first == last && first != PlayManager.position()) {
                            PlayManager.play(first)
                        }
                    }
                }
            })
            mAdapter?.bindToRecyclerView(bottomPlayRcv)
        } else {
            mAdapter?.notifyDataSetChanged()
        }
        bottomPlayRcv.scrollToPosition(PlayManager.position())
    }

    companion object {

        private val TAG = "PlayControlFragment"

        fun newInstance(): PlayControlFragment {
            val args = Bundle()
            val fragment = PlayControlFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
