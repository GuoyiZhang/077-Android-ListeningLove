package com.guoyi.listeninglove.ui.music.local.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.ui.base.BaseFragment;
import com.guoyi.listeninglove.common.Extras;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.player.PlayManager;
import com.guoyi.listeninglove.ui.music.dialog.AddPlaylistDialog;
import com.guoyi.listeninglove.ui.music.dialog.ShowDetailDialog;
import com.guoyi.listeninglove.ui.music.local.adapter.SongAdapter;
import com.guoyi.listeninglove.ui.music.local.contract.AlbumDetailContract;
import com.guoyi.listeninglove.ui.music.local.presenter.AlbumDetailPresenter;
import com.guoyi.listeninglove.bean.Music;
import com.guoyi.listeninglove.ui.music.dialog.AddPlaylistDialog;
import com.guoyi.listeninglove.ui.music.local.presenter.AlbumDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：yonglong on 2016/8/15 19:54
 * 邮箱：643872807@qq.com
 * 版本：2.5
 * 专辑
 */
public class AlbumDetailFragment extends BaseFragment<AlbumDetailPresenter> implements AlbumDetailContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.album_art)
    ImageView album_art;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @OnClick(R.id.fab)
    void onPlayAll() {
        PlayManager.play(0, musicInfos, albumID);
    }

    String albumID;
    String transitionName;
    String title;

    private SongAdapter mAdapter;
    private List<Music> musicInfos = new ArrayList<>();

    public static AlbumDetailFragment newInstance(String id, String title, String transitionName) {
        Bundle args = new Bundle();
        args.putString(Extras.ALBUM_ID, id);
        args.putString(Extras.PLAYLIST_NAME, title);
        args.putString(Extras.TRANSITIONNAME, transitionName);
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void loadData() {
        showLoading();
        mPresenter.loadAlbumSongs(title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_playlist_detail;
    }

    @Override
    public void initViews() {
        albumID = getArguments().getString(Extras.ALBUM_ID);
        transitionName = getArguments().getString(Extras.TRANSITIONNAME);
        title = getArguments().getString(Extras.PLAYLIST_NAME);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (transitionName != null) {
                album_art.setTransitionName(transitionName);
                album_art.setHasTransientState(true);
            }
        }

        if (title != null)
            collapsing_toolbar.setTitle(title);

        setHasOptionsMenu(true);
        if (getActivity() != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.setSupportActionBar(mToolbar);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mAdapter = new SongAdapter(musicInfos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void listener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (view.getId() != R.id.iv_more) {
                PlayManager.play(position, musicInfos, albumID);
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.popup_song_play:
                        PlayManager.play(position, musicInfos, albumID);
                        break;
                    case R.id.popup_song_detail:
                        ShowDetailDialog.newInstance((Music) adapter.getItem(position))
                                .show(getChildFragmentManager(), getTag());
                        break;
                    case R.id.popup_song_addto_queue:
                        AddPlaylistDialog.Companion.newInstance(musicInfos.get(position))
                                .show(getChildFragmentManager(), "ADD_PLAYLIST");
                        break;

                }
                return false;
            });
            popupMenu.inflate(R.menu.popup_album);
            popupMenu.show();
        });
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showEmptyView() {
        mAdapter.setEmptyView(R.layout.view_song_empty);
    }

    @Override
    public void showAlbumSongs(List<Music> songList) {
        musicInfos = songList;
        mAdapter.setNewData(musicInfos);
        hideLoading();
    }


    @Override
    public void showAlbumArt(Drawable albumArt) {
        album_art.setImageDrawable(albumArt);
    }

    @Override
    public void showAlbumArt(Bitmap bitmap) {
        album_art.setImageBitmap(bitmap);
    }

}
