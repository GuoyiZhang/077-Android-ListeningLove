package com.guoyi.listeninglove.ui.music.playpage.fragment

import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.player.PlayManager
import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.ui.base.BaseFragment
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.ui.widget.LyricView
import com.guoyi.listeninglove.utils.SPUtils
import kotlinx.android.synthetic.main.frag_player_lrcview.*

class LyricFragment : BaseFragment<BasePresenter<BaseContract.BaseView>>() {

    val lyricTv by lazy { rootView?.findViewById<LyricView>(R.id.lyricShow) }

    override fun getLayoutId(): Int {
        return R.layout.frag_player_lrcview
    }

    override fun initInjector() {
    }

    override fun initViews() {
        super.initViews()

    }

    /**
     *显示歌词
     */
    fun showLyric(lyric: String?, init: Boolean) {
        if (init) {
            //初始化歌词配置
            lyricShow?.setTextSize(SPUtils.getFontSize())
            lyricShow?.setHighLightTextColor(SPUtils.getFontColor())
            lyricShow?.setTouchable(true)
            lyricShow?.setOnPlayerClickListener { progress, _ ->
                PlayManager.seekTo(progress.toInt())
                if (!PlayManager.isPlaying()) {
                    PlayManager.playPause()
                }
            }
        }
        lyricShow?.setLyricContent(lyric)
    }

    fun setCurrentTimeMillis(current: Long = 0) {
        lyricShow?.setCurrentTimeMillis(current)
    }
}