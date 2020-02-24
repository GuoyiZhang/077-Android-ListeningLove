package com.guoyi.listeninglove.di.component;

import android.app.Activity;
import android.content.Context;

import com.guoyi.listeninglove.di.module.FragmentModule;
import com.guoyi.listeninglove.di.scope.ContextLife;
import com.guoyi.listeninglove.di.scope.PerFragment;
import com.guoyi.listeninglove.ui.music.artist.fragment.ArtistInfoFragment;
import com.guoyi.listeninglove.ui.music.discover.artist.QQArtistListFragment;
import com.guoyi.listeninglove.ui.music.discover.DiscoverFragment;
import com.guoyi.listeninglove.ui.download.ui.DownloadManagerFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.AlbumDetailFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.AlbumFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.ArtistFragment;
import com.guoyi.listeninglove.ui.music.artist.fragment.ArtistSongsFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.LocalVideoFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.FoldersFragment;
import com.guoyi.listeninglove.ui.music.mv.MvListFragment;
import com.guoyi.listeninglove.ui.music.charts.fragment.ChartsDetailFragment;
import com.guoyi.listeninglove.ui.music.mv.MvSearchListFragment;
import com.guoyi.listeninglove.ui.music.playlist.love.LoveFragment;
import com.guoyi.listeninglove.ui.music.my.MyMusicFragment;
import com.guoyi.listeninglove.ui.music.bottom.PlayControlFragment;
import com.guoyi.listeninglove.ui.music.playlist.detail.PlaylistDetailFragment;
import com.guoyi.listeninglove.ui.music.playlist.PlaylistFragment;
import com.guoyi.listeninglove.ui.music.playlist.history.RecentlyFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.SongsFragment;
import com.guoyi.listeninglove.ui.download.ui.DownloadedFragment;
import com.guoyi.listeninglove.ui.music.playlist.square.TopPlaylistFragment;
import com.guoyi.listeninglove.ui.music.playqueue.PlayQueueFragment;
import com.guoyi.listeninglove.ui.music.search.fragment.SearchSongsFragment;
import com.guoyi.listeninglove.ui.music.search.fragment.YoutubeSearchFragment;
import com.guoyi.listeninglove.ui.my.BindLoginActivity;
import com.guoyi.listeninglove.ui.download.ui.DownloadManagerFragment;
import com.guoyi.listeninglove.ui.download.ui.DownloadedFragment;
import com.guoyi.listeninglove.ui.music.artist.fragment.ArtistInfoFragment;
import com.guoyi.listeninglove.ui.music.artist.fragment.ArtistSongsFragment;
import com.guoyi.listeninglove.ui.music.bottom.PlayControlFragment;
import com.guoyi.listeninglove.ui.music.charts.fragment.ChartsDetailFragment;
import com.guoyi.listeninglove.ui.music.discover.DiscoverFragment;
import com.guoyi.listeninglove.ui.music.discover.artist.QQArtistListFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.AlbumFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.ArtistFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.FoldersFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.LocalVideoFragment;
import com.guoyi.listeninglove.ui.music.local.fragment.SongsFragment;
import com.guoyi.listeninglove.ui.music.my.MyMusicFragment;
import com.guoyi.listeninglove.ui.music.playlist.PlaylistFragment;
import com.guoyi.listeninglove.ui.music.playlist.square.TopPlaylistFragment;
import com.guoyi.listeninglove.ui.music.search.fragment.SearchSongsFragment;
import com.guoyi.listeninglove.ui.music.search.fragment.YoutubeSearchFragment;
import com.guoyi.listeninglove.ui.my.BindLoginActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Component;

/**
 * Created by lw on 2017/1/19.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(AlbumDetailFragment fragment);

    void inject(AlbumFragment fragment);

    void inject(ArtistFragment artistFragment);

    void inject(ArtistSongsFragment artistSongsFragment);

    void inject(FoldersFragment foldersFragment);

    void inject(RecentlyFragment recentlyFragment);

    void inject(PlaylistDetailFragment playlistDetailFragment);

    void inject(SongsFragment songsFragment);

    void inject(PlayControlFragment playControlFragment);

    void inject(MyMusicFragment myMusicFragment);

    void inject(LoveFragment loveFragment);

    void inject(LocalVideoFragment localVideoFragment);

    void inject(DownloadedFragment downloadedFragment);

    void inject(DiscoverFragment discoverFragment);

    void inject(PlayQueueFragment playQueueFragment);

    void inject(DownloadManagerFragment downloadManagerFragment);

    void inject(MvListFragment mvListFragment);

    void inject(@NotNull ChartsDetailFragment chartsDetailFragment);

    void inject(@NotNull QQArtistListFragment QQArtistListFragment);

    void inject(MvSearchListFragment mvSearchListFragment);

    void inject(@NotNull BindLoginActivity bindLoginActivity);

    void inject(@NotNull PlaylistFragment playlistFragment);

    void inject(@NotNull TopPlaylistFragment topPlaylistFragment);

    void inject(@NotNull ArtistInfoFragment artistInfoFragment);

    void inject(@NotNull com.guoyi.listeninglove.ui.music.artist.fragment.AlbumFragment albumFragment);

    void inject(@NotNull SearchSongsFragment searchSongsFragment);

    void inject(@NotNull YoutubeSearchFragment youtubeSearchFragment);
}
