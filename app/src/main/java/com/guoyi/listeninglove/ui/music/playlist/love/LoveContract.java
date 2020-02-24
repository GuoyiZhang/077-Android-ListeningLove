package com.guoyi.listeninglove.ui.music.playlist.love;

import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Music;

import java.util.List;

public interface LoveContract {

    interface View extends BaseContract.BaseView {

        void showSongs(List<Music> songs);

        void showEmptyView();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSongs();

        void clearHistory();
    }
}
