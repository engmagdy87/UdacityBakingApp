package com.baking.mm.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baking.mm.bakingapp.javacalsses.RecipeMediaFragment;
import com.baking.mm.bakingapp.javacalsses.RecipeStepFragment;
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
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeStepsNav extends AppCompatActivity {
    ArrayList<RecipeSteps> recipeSteps;
    private String recipeName;
    int index = 0;

    TextView counter;
    Button next;
    Button previous;

    private static final String ONSAVEINSTANCESTATE_RECIPESTEPS_KEY = "steps";
    private static final String ONSAVEINSTANCESTATE_STEP_INDEX_KEY = "index";
    private static final String ONSAVEINSTANCESTATE_RECIPENAME_KEY = "recipename";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ONSAVEINSTANCESTATE_RECIPESTEPS_KEY, recipeSteps);
        outState.putString(ONSAVEINSTANCESTATE_RECIPENAME_KEY,recipeName);
        outState.putInt(ONSAVEINSTANCESTATE_STEP_INDEX_KEY,index);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_browsing);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ONSAVEINSTANCESTATE_RECIPENAME_KEY)) {
                recipeName = savedInstanceState.getString(ONSAVEINSTANCESTATE_RECIPENAME_KEY);
                recipeSteps = savedInstanceState.getParcelableArrayList(ONSAVEINSTANCESTATE_RECIPESTEPS_KEY);
                index = savedInstanceState.getInt(ONSAVEINSTANCESTATE_STEP_INDEX_KEY);
                getSupportActionBar().setTitle(recipeName);
            }
        } else {
        Intent myIntent = getIntent();

        Bundle extras = myIntent.getExtras();
        if (extras != null) {
            if (extras.containsKey("name")) {
                recipeName = myIntent.getStringExtra("name");
                getSupportActionBar().setTitle(recipeName);
            }
            if (extras.containsKey("steps")) {
                recipeSteps = myIntent.getParcelableArrayListExtra("steps");
            }
            if (extras.containsKey("clicked_item")) {
                index = myIntent.getIntExtra("clicked_item", 0);
            }
        }
    }

        final RecipeMediaFragment recipeMediaFragment = new RecipeMediaFragment();
        recipeMediaFragment.setRecipeVideo(recipeSteps);
        recipeMediaFragment.setIndex(index);

        final RecipeStepFragment recipeStepsFragment = new RecipeStepFragment();
        recipeStepsFragment.setRecipeStep(recipeSteps);
        recipeStepsFragment.setIndex(index);

        counter = findViewById(R.id.tv_steps_counter);
        counter.setText(String.valueOf(index+1)+"/"+recipeSteps.size());
        next = findViewById(R.id.btn_next);
        previous = findViewById(R.id.btn_prev);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index < recipeSteps.size()-1) {
                    index++;
                } else {
                    index = 0;
                }
                recipeStepsFragment.setIndex(index);
                recipeStepsFragment.changeFragment();
                recipeMediaFragment.setIndex(index);
                recipeMediaFragment.changeFragment();
                counter.setText(String.valueOf(index+1)+"/"+recipeSteps.size());
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index > 0) {
                    index--;
                } else {
                    index = recipeSteps.size()-1;
                }
                recipeStepsFragment.setIndex(index);
                recipeStepsFragment.changeFragment();
                recipeMediaFragment.setIndex(index);
                recipeMediaFragment.changeFragment();
                counter.setText(String.valueOf(index+1)+"/"+recipeSteps.size());
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.media_container, recipeMediaFragment).commit();

        fragmentManager.beginTransaction()
                .add(R.id.step_container, recipeStepsFragment).commit();
    }
    
}
