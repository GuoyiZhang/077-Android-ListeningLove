package com.guoyi.listeninglove.ui.music.artist.contract;


import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Music;

public interface ArtistInfoContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadArtistInfo(Music music);
    }

}
