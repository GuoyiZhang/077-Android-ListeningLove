package com.guoyi.listeninglove.ui.music.local.contract;


import com.guoyi.listeninglove.bean.Artist;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Artist;

import java.util.List;

public interface ArtistContract {

    interface View extends BaseContract.BaseView {

        void showArtists(List<Artist> artists);

        void showEmptyView();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadArtists(String action);
    }
}
