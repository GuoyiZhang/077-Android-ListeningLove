package com.guoyi.listeninglove.ui.music.discover;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.bean.Playlist;
import com.guoyi.listeninglove.utils.CoverLoader;
import com.guoyi.listeninglove.bean.Playlist;
import com.guoyi.listeninglove.utils.CoverLoader;

import java.util.List;

/**
 * 作者：yonglong on 2016/8/10 21:36
 * 邮箱：643872807@qq.com
 * 版本：2.5
 */
public class BaiduRadioAdapter extends BaseQuickAdapter<Playlist, BaseViewHolder> {

    public BaiduRadioAdapter(List<Playlist> list) {
        super(R.layout.item_top_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Playlist channel) {
        helper.setText(R.id.title, channel.getName());
        helper.setVisible(R.id.title, true);
        CoverLoader.INSTANCE.loadImageView(mContext, channel.getCoverUrl(), helper.getView(R.id.iv_cover));
    }

}