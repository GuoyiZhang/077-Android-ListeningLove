package com.guoyi.listeninglove.ui.youtube;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.utils.LogUtil;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YoutubeActivity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        videoId = getIntent().getStringExtra("videoId");
        LogUtil.d("YoutubeActivity", "|videoId " + videoId);
        initYouTubePlayerView();
    }


    private void initYouTubePlayerView() {
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        getLifecycle().addObserver(youTubePlayerView);
        initPictureInPicture(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        videoId, 0f
                );
            }
        });
    }

    private void initPictureInPicture(YouTubePlayerView youTubePlayerView) {
        ImageView pictureInPictureIcon = new ImageView(this);
        pictureInPictureIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_picture_in_picture));

        pictureInPictureIcon.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                boolean supportsPIP = getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
                if (supportsPIP)
                    enterPictureInPictureMode();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Can't enter picture in picture mode")
                        .setMessage("In order to enter picture in picture mode you need a SDK version >= N.")
                        .show();
            }
        });

        youTubePlayerView.getPlayerUiController().addView(pictureInPictureIcon);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        if (isInPictureInPictureMode) {
            youTubePlayerView.enterFullScreen();
            youTubePlayerView.getPlayerUiController().showUi(false);
        } else {
            youTubePlayerView.exitFullScreen();
            youTubePlayerView.getPlayerUiController().showUi(true);
        }
    }
}
