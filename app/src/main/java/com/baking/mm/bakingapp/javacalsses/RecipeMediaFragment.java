package com.baking.mm.bakingapp.javacalsses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.RecipeDetails;
import com.baking.mm.bakingapp.RecipeStepsNav;
import com.baking.mm.bakingapp.pojo.RecipeSteps;
import com.google.android.exoplayer2.C;
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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeMediaFragment extends Fragment implements ExoPlayer.EventListener {
    public List<RecipeSteps> mRecipeSteps;
    SimpleExoPlayerView videoView;
    ImageView imageView;
    long playerPosition = 0;
    public int index;
    private boolean twoPanes;
    SimpleExoPlayer player;
    String path;
    View rootView;
    View rootLayout;
    public RecipeMediaFragment() {

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("PLAYERPOSITION", playerPosition);
        outState.putString("VIDEOPATH", path);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_media, container, false);
        rootLayout = inflater.inflate(R.layout.recipe_ingredients_steps, container, false);
        videoView = rootView.findViewById(R.id.player_view);
        imageView = rootView.findViewById(R.id.image_view);

        if (savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong("PLAYERPOSITION");
            path = savedInstanceState.getString("VIDEOPATH");
        } else {
            if (rootLayout.findViewById(R.id.ll_two_panes) != null) {
                twoPanes = true;
                if (mRecipeSteps != null) {
                    setPath(mRecipeSteps);
                } else {
                    setRecipeVideo(((RecipeDetails) getContext()).getRecipes());
                    setIndex(((RecipeDetails) getContext()).getIndex());
                    setPath(mRecipeSteps);
                }
            } else {
                twoPanes = false;
                if (mRecipeSteps != null) {
                    setPath(mRecipeSteps);
                } else {
                    setRecipeVideo(((RecipeStepsNav) getContext()).getRecipes());
                    setIndex(((RecipeStepsNav) getContext()).getIndex());
                    setPath(mRecipeSteps);
                }
            }

            if (path.equals("")) {
                if (!mRecipeSteps.get(index).thumbnailURL.equals("")) {
                    if (mRecipeSteps.get(index).thumbnailURL.contains(".mp4")) {
                        //image key is handled as an image for CardView in RecipeCardAdapter class @ line 68
                        path = mRecipeSteps.get(index).thumbnailURL.toString();
                        imageView.setVisibility(View.INVISIBLE);
                        videoView.setVisibility(View.VISIBLE);
                        videoPlayer(rootView);
                    } else
                        Picasso.with(getContext()).load(mRecipeSteps.get(index).thumbnailURL).into(imageView);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);
                }
            } else {
                imageView.setVisibility(View.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoPlayer(rootView);
            }
        }

        return rootView;
    }

    public void changeFragment() {
        this.onDestroy();
        if (mRecipeSteps != null) {
            setPath(mRecipeSteps);
        } else {
            setRecipeVideo(((RecipeStepsNav) getContext()).getRecipes());
            setIndex(((RecipeStepsNav) getContext()).getIndex());
            setPath(mRecipeSteps);
        }
        if (path.equals("")) {
            if (!mRecipeSteps.get(index).thumbnailURL.equals("")) {
                if (mRecipeSteps.get(index).thumbnailURL.contains(".mp4")) {
                    //image key is handled as an image for CardView in RecipeCardAdapter class @ line 68
                    path = mRecipeSteps.get(index).thumbnailURL.toString();
                    imageView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    videoPlayer(rootView);
                } else
                    Picasso.with(getContext()).load(mRecipeSteps.get(index).thumbnailURL).into(imageView);
            } else {
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.INVISIBLE);
            }

        } else {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoPlayer(rootView);
        }

    }

    public void setPath(List<RecipeSteps> mRecipeSteps) {
        if (mRecipeSteps.get(index).videoURL.toString().isEmpty() || mRecipeSteps.get(index).videoURL.toString().equals(""))
            path = "";
        else
            path = mRecipeSteps.get(index).videoURL.toString();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRecipeVideo(List<RecipeSteps> mRecipeSteps) {
        this.mRecipeSteps = mRecipeSteps;
    }

    public void videoPlayer(View view) {

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        SimpleExoPlayerView playerView = view.findViewById(R.id.player_view);

        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "Android-ExoPlayer");

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(path), dataSourceFactory, extractorsFactory, null, null);
        if (playerPosition != 0)
            player.seekTo(playerPosition);
        player.addListener(this);
        player.prepare(videoSource);

        playerView.requestFocus();
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        playerPosition = player.getCurrentPosition();
        player.release();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        player.setPlayWhenReady(true);
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        player.setPlayWhenReady(true);
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        player.setPlayWhenReady(true);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        player.release();
    }
}
