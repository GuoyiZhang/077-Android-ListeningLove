package com.guoyi.listeninglove.ui.theme;

import com.guoyi.listeninglove.utils.SPUtils;

/**
 * 作者：yonglong
 * 包名：com.guoyi.listeninglove.ui.theme
 * 时间：2019/4/9 13:28
 * 描述：
 */
public class ThemeStore {
    public final static int DAY = 0;
    public final static int NIGHT = 1;

    public static int THEME_MODE = DAY;

    public static void updateThemeMode() {
        SPUtils.putAnyCommit(SPUtils.SP_KEY_THEME_MODE, THEME_MODE);
    }

    public static int getThemeMode() {
        return SPUtils.getAnyByKey(SPUtils.SP_KEY_THEME_MODE, DAY);
    }
}
