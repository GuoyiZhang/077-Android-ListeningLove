package com.guoyi.listeninglove.di.component;

import android.app.Activity;
import android.content.Context;

import com.guoyi.listeninglove.di.module.ActivityModule;
import com.guoyi.listeninglove.di.scope.ContextLife;
import com.guoyi.listeninglove.di.scope.PerActivity;
import com.guoyi.listeninglove.ui.chat.ChatActivity;
import com.guoyi.listeninglove.ui.chat.ChatDetailActivity;
import com.guoyi.listeninglove.ui.music.artist.activity.ArtistDetailActivity;
import com.guoyi.listeninglove.ui.music.charts.activity.BaiduMusicListActivity;
import com.guoyi.listeninglove.ui.music.charts.activity.BasePlaylistActivity;
import com.guoyi.listeninglove.ui.music.edit.EditSongListActivity;
import com.guoyi.listeninglove.ui.music.mv.BaiduMvDetailActivity;
import com.guoyi.listeninglove.ui.music.mv.KugouMvDetailActivity;
import com.guoyi.listeninglove.ui.music.mv.MvDetailActivity;
import com.guoyi.listeninglove.ui.music.playlist.detail.PlaylistDetailActivity;
import com.guoyi.listeninglove.ui.music.playpage.LockScreenPlayerActivity;
import com.guoyi.listeninglove.ui.music.playpage.PlayerActivity;
import com.guoyi.listeninglove.ui.music.search.PlaylistSearchActivity;
import com.guoyi.listeninglove.ui.music.search.SearchActivity;
import com.guoyi.listeninglove.ui.my.BindLoginActivity;
import com.guoyi.listeninglove.ui.my.LoginActivity;
import com.guoyi.listeninglove.ui.my.RegisterActivity;
import com.guoyi.listeninglove.ui.chat.ChatActivity;
import com.guoyi.listeninglove.ui.chat.ChatDetailActivity;
import com.guoyi.listeninglove.ui.music.artist.activity.ArtistDetailActivity;
import com.guoyi.listeninglove.ui.music.charts.activity.BaiduMusicListActivity;
import com.guoyi.listeninglove.ui.music.charts.activity.BasePlaylistActivity;
import com.guoyi.listeninglove.ui.music.edit.EditSongListActivity;
import com.guoyi.listeninglove.ui.music.mv.BaiduMvDetailActivity;
import com.guoyi.listeninglove.ui.music.playlist.detail.PlaylistDetailActivity;
import com.guoyi.listeninglove.ui.music.playpage.LockScreenPlayerActivity;
import com.guoyi.listeninglove.ui.music.playpage.PlayerActivity;
import com.guoyi.listeninglove.ui.music.search.SearchActivity;
import com.guoyi.listeninglove.ui.my.BindLoginActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Component;

/**
 * Created by lw on 2017/1/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(BaiduMusicListActivity baiduMusicListActivity);


    void inject(RegisterActivity registerActivity);

    void inject(LoginActivity loginActivity);

    void inject(SearchActivity searchActivity);

    void inject(BasePlaylistActivity basePlaylistActivity);

    void inject(MvDetailActivity mvDetailActivity);

    void inject(@NotNull PlayerActivity playerActivity);

    void inject(PlaylistDetailActivity playlistDetailActivity);

    void inject(ArtistDetailActivity playlistDetailActivity);

    void inject(EditSongListActivity editMusicActivity);

    void inject(@NotNull ChatActivity chatActivity);

    void inject(@NotNull ChatDetailActivity chatDetailActivity);

    void inject(@NotNull BaiduMvDetailActivity baiduMvDetailActivity);

    void inject(@NotNull LockScreenPlayerActivity lockScreenPlayerActivity);

    void inject(@NotNull BindLoginActivity bindLoginActivity);

    void inject(@NotNull KugouMvDetailActivity kugouMvDetailActivity);
}
