package com.guoyi.listeninglove.ui.music.playlist.love;

import com.guoyi.listeninglove.ui.base.BasePresenter;
import com.guoyi.listeninglove.data.SongLoader;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.data.SongLoader;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yonglong on 2018/1/7.
 */

public class LovePresenter extends BasePresenter<LoveContract.View> implements LoveContract.Presenter {

    @Inject
    public LovePresenter() {
    }

    @Override
    public void loadSongs() {
        List<Music> songs = SongLoader.INSTANCE.getFavoriteSong();
        mView.showSongs(songs);
    }

    @Override
    public void clearHistory() {

    }
}
