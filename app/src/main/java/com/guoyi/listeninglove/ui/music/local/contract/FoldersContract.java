package com.guoyi.listeninglove.ui.music.local.contract;

import com.guoyi.listeninglove.bean.FolderInfo;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.bean.FolderInfo;
import com.guoyi.listeninglove.bean.Music;

import java.util.List;

/**
 * Created by D22434 on 2018/1/8.
 */

public interface FoldersContract {

    interface View extends BaseContract.BaseView {

        void showEmptyView();
        void showSongs(List<Music> musicList);

        void showFolders(List<FolderInfo> folderInfos);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadSongs(String path);
        void loadFolders();
    }
}
