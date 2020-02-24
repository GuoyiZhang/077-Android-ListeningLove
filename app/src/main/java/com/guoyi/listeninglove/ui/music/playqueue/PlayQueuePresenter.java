package com.guoyi.listeninglove.ui.music.playqueue;

import com.guoyi.listeninglove.ui.base.BasePresenter;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.player.PlayManager;
import com.guoyi.listeninglove.bean.Music;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by yonglong on 2018/1/7.
 */

public class PlayQueuePresenter extends BasePresenter<PlayQueueContract.View> implements PlayQueueContract.Presenter {

    @Inject
    public PlayQueuePresenter() {
    }

    @Override
    public void attachView(PlayQueueContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void loadSongs() {
        List<Music> musicList = PlayManager.getPlayList();
        mView.showSongs(musicList);
    }

    @Override
    public void clearQueue() {
        PlayManager.clearQueue();
    }
}
