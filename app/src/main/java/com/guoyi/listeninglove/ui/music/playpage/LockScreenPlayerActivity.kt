package com.guoyi.listeninglove.ui.music.playpage

import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.common.TransitionAnimationUtils
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.base.BaseActivity
import com.guoyi.listeninglove.utils.FormatUtil
import kotlinx.android.synthetic.main.activity_lock_screen.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Des    : 播放界面
 * Author : master.
 * Date   : 2018/5/19 .
 */
class LockScreenPlayerActivity : BaseActivity<PlayPresenter>(), PlayContract.View {

    override fun setPlayingBitmap(albumArt: Bitmap?) {
    }

    override fun setPlayingBg(albumArt: Drawable?, isInit: Boolean?) {
        if (isInit != null && isInit) {
            playerBackgroundIv.setImageDrawable(albumArt)
        } else {
            //加载背景图过度
            TransitionAnimationUtils.startChangeAnimation(playerBackgroundIv, albumArt)
        }
    }

    override fun updatePlayStatus(isPlaying: Boolean) {
        playPauseIv.setImageResource(if (isPlaying) R.drawable.ic_play else R.drawable.ic_pause);
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_lock_screen
    }

    override fun initView() {
        prevIv.setOnClickListener {
            PlayManager.prev()
        }
        playPauseIv.setOnClickListener {
            PlayManager.playPause()
        }
        nextIv.setOnClickListener {
            PlayManager.next()
        }
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }


    override fun updateProgress(progress: Long, max: Long) {
        //获取当前时间
        timeTv.text = simpleDateFormat?.format(Date(System.currentTimeMillis()))
    }

    override fun showNowPlaying(music: Music?) {
        songNameTv.text = music?.title
        singerTv.text = music?.artist
    }

    private var animationDrawable: AnimationDrawable? = null
    private var simpleDateFormat: SimpleDateFormat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //屏蔽系统的锁屏界面，将此activity设置为锁屏界面
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD, WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    /**
     * 禁用返回键
     */
    override fun onBackPressed() {
    }

    override fun initData() {
        //获取当前时间
        dateTv.text = FormatUtil.formatDate(Date(), "EEEE, dd MMMM").toString()
        simpleDateFormat = SimpleDateFormat("HH:mm")
        timeTv.text = simpleDateFormat?.format(Date(System.currentTimeMillis()))
    }

    override fun onPause() {
        super.onPause()
        animationDrawable?.stop()
    }

    override fun onResume() {
        super.onResume()
        animationDrawable?.start()
    }


}
