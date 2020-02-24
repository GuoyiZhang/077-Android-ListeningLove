package com.guoyi.listeninglove.ui.music.edit

import com.afollestad.materialdialogs.MaterialDialog
import com.guoyi.listeninglove.R
import com.guoyi.listeninglove.bean.Music
import com.guoyi.listeninglove.data.db.DaoLitepal
import com.guoyi.listeninglove.common.Extras
import com.guoyi.listeninglove.ui.base.BaseActivity
import com.guoyi.listeninglove.ui.base.BaseContract
import com.guoyi.listeninglove.ui.base.BasePresenter
import com.guoyi.listeninglove.utils.Mp3Util
import com.guoyi.listeninglove.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_music_edit.*

/**
 * Des    :
 * Author : master.
 * Date   : 2018/8/26 .
 */
class EditMusicActivity : BaseActivity<BasePresenter<BaseContract.BaseView>>() {

    var music: Music? = null

    override fun getLayoutResID(): Int {
        return R.layout.activity_music_edit
    }

    override fun setToolbarTitle(): String {
        return getString(R.string.tag_title)
    }

    override fun initView() {
        saveTagBtn.setOnClickListener {

            MaterialDialog(this).show {
                title(R.string.warning)
                message(R.string.tag_edit_tips)
                positiveButton(R.string.sure) {
                    music?.title = titleInputView.editText?.text.toString()
                    music?.artist = artistInputView.editText?.text.toString()
                    music?.album = albumInputView.editText?.text.toString()
                    music?.let { it1 ->
                        if (updateTagInfo(it1)) {
                            ToastUtils.show(getString(R.string.tag_edit_success))
                        } else {
                            ToastUtils.show(getString(R.string.tag_edit_tips))
                        }
                        this@EditMusicActivity.finish()
                    }
                }
            }
        }
    }

    override fun initData() {
        music = intent.getParcelableExtra(Extras.SONG)
        titleInputView.editText?.setText(music?.title)
        artistInputView.editText?.setText(music?.artist)
        albumInputView.editText?.setText(music?.album)

        music?.uri?.let { Mp3Util.getTagInfo(it) }
    }

    override fun initInjector() {
    }

    private fun updateTagInfo(music: Music): Boolean {
        if (music.uri == null) return false
        val result = Mp3Util.updateTagInfo(music.uri!!, music)
        Mp3Util.getTagInfo(music.uri!!)
        if (result) {
            DaoLitepal.saveOrUpdateMusic(music)
        }
        return result
    }
}