package com.guoyi.listeninglove.ui.youtube;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.guoyi.listeninglove.R;
import com.guoyi.listeninglove.utils.FormatUtil;
import com.guoyi.listeninglove.utils.LogUtil;
import com.guoyi.listeninglove.utils.FormatUtil;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/**
 * 作者：yonglong
 * 包名：com.guoyi.listeninglove.ui.youtube
 * 时间：2019/7/15 11:45
 * 描述：
 */
public class CustomPlayerUiController extends AbstractYouTubePlayerListener implements YouTubePlayerFullScreenListener {

    private final View playerUi;

    private Context context;
    private YouTubePlayer youTubePlayer;
    private YouTubePlayerView youTubePlayerView;

    // panel is used to intercept clicks on the WebView, I don't want the user to be able to click the WebView directly.
    private View panel;
    private View progressbar;
    private TextView videoCurrentTimeTextView;
    private TextView videoDurationTextView;

    private final YouTubePlayerTracker playerTracker;
    private boolean fullscreen = false;

    private final String TAG = "CustomPlayerUiController";

    CustomPlayerUiController(Context context, View customPlayerUi, YouTubePlayer youTubePlayer, YouTubePlayerView youTubePlayerView) {
        this.playerUi = customPlayerUi;
        this.context = context;
        this.youTubePlayer = youTubePlayer;
        this.youTubePlayerView = youTubePlayerView;

        playerTracker = new YouTubePlayerTracker();
        youTubePlayer.addListener(playerTracker);
        initViews(customPlayerUi);
    }

    private void initViews(View playerUi) {
        panel = playerUi.findViewById(R.id.panel);
        progressbar = playerUi.findViewById(R.id.progressbar);
        videoCurrentTimeTextView = playerUi.findViewById(R.id.video_current_time);
        videoDurationTextView = playerUi.findViewById(R.id.video_duration);
        ImageView playPauseButton = playerUi.findViewById(R.id.play_pause_button);
        ImageView enterExitFullscreenButton = playerUi.findViewById(R.id.enter_exit_fullscreen_button);

        playPauseButton.setOnClickListener((view) -> {
            if (playerTracker.getState() == PlayerConstants.PlayerState.PLAYING) {
                youTubePlayer.pause();
                playPauseButton.setImageResource(R.drawable.ic_play);
            } else {
                playPauseButton.setImageResource(R.drawable.ic_pause);
                youTubePlayer.play();
            }
        });

        enterExitFullscreenButton.setOnClickListener((view) -> {
            if (fullscreen) youTubePlayerView.exitFullScreen();
            else youTubePlayerView.enterFullScreen();

            fullscreen = !fullscreen;
        });
    }

    @Override
    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
        progressbar.setVisibility(View.GONE);
        LogUtil.d(TAG, "");
    }

    @Override
    public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
        if (state == PlayerConstants.PlayerState.PLAYING || state == PlayerConstants.PlayerState.PAUSED || state == PlayerConstants.PlayerState.VIDEO_CUED)
            panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        else if (state == PlayerConstants.PlayerState.BUFFERING)
            panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
        videoCurrentTimeTextView.setText(FormatUtil.INSTANCE.formatTime((long) second));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float duration) {
        videoDurationTextView.setText(FormatUtil.INSTANCE.formatTime((long) duration));
    }

    @Override
    public void onYouTubePlayerEnterFullScreen() {
        ViewGroup.LayoutParams viewParams = playerUi.getLayoutParams();
        viewParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        playerUi.setLayoutParams(viewParams);
    }

    @Override
    public void onYouTubePlayerExitFullScreen() {
        ViewGroup.LayoutParams viewParams = playerUi.getLayoutParams();
        viewParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        playerUi.setLayoutParams(viewParams);
    }
}