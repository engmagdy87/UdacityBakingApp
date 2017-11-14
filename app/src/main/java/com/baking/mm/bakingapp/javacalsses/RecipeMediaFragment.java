package com.baking.mm.bakingapp.javacalsses;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.pojo.RecipeSteps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.List;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeMediaFragment extends Fragment implements ExoPlayer.EventListener {
    public List<RecipeSteps> mRecipeSteps;
    SimpleExoPlayerView videoView;
    public int index;

    SimpleExoPlayer player;
    String path;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_media, container, false);
        videoView = rootView.findViewById(R.id.player_view);

        if (mRecipeSteps != null) {
            if (mRecipeSteps.get(index).videoURL.toString().isEmpty() || mRecipeSteps.get(index).videoURL.toString().equals(""))
                path="";
            else
                path = mRecipeSteps.get(index).videoURL.toString();
        }
        videoPlayer(rootView);
        return rootView;

    }

    public void changeFragment() {
        this.onDestroy();
        if (mRecipeSteps != null) {
            if (mRecipeSteps.get(index).videoURL.toString().isEmpty() || mRecipeSteps.get(index).videoURL.toString().equals(""))
                path="";
            else
                path = mRecipeSteps.get(index).videoURL.toString();
        }
        videoPlayer(rootView);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRecipeVideo(List<RecipeSteps> mRecipeSteps) {
        this.mRecipeSteps = mRecipeSteps;
    }

    public void videoPlayer(View view){

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        SimpleExoPlayerView playerView = view.findViewById(R.id.player_view);
        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "Android-ExoPlayer");

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(path), dataSourceFactory, extractorsFactory, null, null);

        player.addListener(this);
        player.prepare(videoSource);
        playerView.requestFocus();
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                break;
            case ExoPlayer.STATE_IDLE:
                break;
            case ExoPlayer.STATE_READY:
                break;
            case ExoPlayer.STATE_ENDED:
                break;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
