package com.guoyi.listeninglove.ui.music.charts.contract;


import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.Playlist;
import com.guoyi.listeninglove.ui.music.charts.GroupItemData;

import java.util.List;

public interface OnlinePlaylistContract {

    interface View extends BaseContract.BaseView {
        void showErrorInfo(String msg);

        void showNeteaseCharts(List<GroupItemData> charts);

        void showQQCharts(List<GroupItemData> charts);

        void showBaiduCharts(List<GroupItemData> charts);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadBaiDuPlaylist();

        void loadTopList();

        void loadQQList();
    }
}
