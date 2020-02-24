package com.guoyi.listeninglove.ui.music.playqueue;

import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.bean.Music;

import java.util.List;

public interface PlayQueueContract {

    interface View extends BaseContract.BaseView {
        void showSongs(List<Music> songs);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSongs();

        void clearQueue();
    }
}
