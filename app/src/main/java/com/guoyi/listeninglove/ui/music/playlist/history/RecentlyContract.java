package com.guoyi.listeninglove.ui.music.playlist.history;

import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Music;

import java.util.List;

public interface RecentlyContract {

    interface View extends BaseContract.BaseView {

        void showSongs(List<Music> songs);

        void showEmptyView();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSongs();

        void clearHistory();
    }
}
