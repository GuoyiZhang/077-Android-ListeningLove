package com.guoyi.listeninglove.ui.my;

import android.app.Activity;
import android.content.Intent;

import com.guoyi.musicapi.netease.LoginInfo;
import com.guoyi.listeninglove.ui.base.BaseContract;
import com.guoyi.listeninglove.ui.my.user.User;
import com.guoyi.listeninglove.ui.my.user.User;

import java.util.Map;

/**
 * Created by D22434 on 2018/1/3.
 */

public interface LoginContract {

    interface View extends BaseContract.BaseView {
        void showErrorInfo(String msg);

        void success(User user);

        void bindSuccess(LoginInfo loginInfo);
    }

    interface Presenter extends BaseContract.BasePresenter<LoginContract.View> {
        void login(Map<String, String> params);

        void loginByQQ(Activity activity);

        void bindNetease(String userName, String pwd);

        void onActivityResult(int requestCode, int resultCode, Intent data);

    }

}
