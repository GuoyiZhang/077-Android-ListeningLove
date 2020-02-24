package com.guoyi.listeninglove.ui.music.local.contract;

import com.guoyi.listeninglove.bean.Album;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Album;

import java.util.List;

public interface AlbumsContract {

    interface View extends BaseContract.BaseView {

        void showAlbums(List<Album> albumList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadAlbums(String action);

    }
}
