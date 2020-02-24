package com.guoyi.listeninglove.ui.music.playpage.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Des    :
 * Author : master.
 * Date   : 2018/6/6 .
 */
public class SongCommetFragment extends BaseFragment {

    @BindView(R.id.rv_comment)
    RecyclerView mCommentRsv;

    @Override
    public int getLayoutId() {
        return R.layout.frag_player_comment;
    }

    @Override
    public void initViews() {
        mCommentRsv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initInjector() {

    }

}
