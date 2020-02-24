package com.guoyi.listeninglove.ui.music.mv;

import com.guoyi.musicapi.netease.CommentsItemInfo;
import com.guoyi.musicapi.netease.MvInfoDetail;
import com.guoyi.musicapi.netease.MvInfoDetailInfo;
import com.guoyi.listeninglove.bean.MvInfoBean;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.bean.MvInfoBean;

import java.util.List;


public interface MvDetailContract {

    interface View extends BaseContract.BaseView {
        void showMvList(List<MvInfoDetail> mvList);

        void showBaiduMvDetailInfo(MvInfoBean mvInfoBean);

        void showMvDetailInfo(MvInfoDetailInfo mvInfoDetailInfo);

        void showMvHotComment(List<CommentsItemInfo> mvHotCommentInfo);

        void showMvComment(List<CommentsItemInfo> mvCommentInfo);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadMv(int offset);

        void loadMvDetail(String mvid);

        void loadBaiduMvInfo(String songId);

        void loadSimilarMv(String mvid);

        void loadMvComment(String mvid);
    }
}
