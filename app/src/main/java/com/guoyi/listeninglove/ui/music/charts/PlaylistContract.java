package com.guoyi.listeninglove.ui.music.charts;


import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.bean.Playlist;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.bean.Playlist;

import java.util.List;

public interface PlaylistContract {

    interface View extends BaseContract.BaseView {

        void showPlayList(Playlist playlist);

        void showOnlineMusicList(List<Music> musicList);

        void showNeteaseCharts(List<Playlist> playlistList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadPlaylist(String idx, String type);

        void loadNeteasePlaylist(String id);

        void loadOnlineMusicList(String type, int limit, int mOffset);
    }
}
