package com.baking.mm.bakingapp;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeStepsNav extends AppCompatActivity {
    ArrayList<RecipeSteps> recipeSteps;
    private String recipeName;
    public long playerPosition = 0;
    public String path="";
    int index = 0;
    TextView counter;
    Button next;
    Button previous;
    FragmentManager fragmentManager = getSupportFragmentManager();
    RecipeMediaFragment recipeMediaFragment;
    RecipeStepFragment recipeStepsFragment;

    private static final String ONSAVEINSTANCESTATE_RECIPESTEPS_KEY = "steps";
    private static final String ONSAVEINSTANCESTATE_STEP_INDEX_KEY = "index";
    private static final String ONSAVEINSTANCESTATE_RECIPENAME_KEY = "recipename";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ONSAVEINSTANCESTATE_RECIPESTEPS_KEY, recipeSteps);
        outState.putString(ONSAVEINSTANCESTATE_RECIPENAME_KEY, recipeName);
        outState.putString("VIDEOPATH", path);
        outState.putInt(ONSAVEINSTANCESTATE_STEP_INDEX_KEY, index);
        outState.putLong("PLAYERPOSITION", playerPosition);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_browsing);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ONSAVEINSTANCESTATE_RECIPENAME_KEY)) {
                recipeName = savedInstanceState.getString(ONSAVEINSTANCESTATE_RECIPENAME_KEY);
                path = savedInstanceState.getString("VIDEOPATH");
                recipeSteps = savedInstanceState.getParcelableArrayList(ONSAVEINSTANCESTATE_RECIPESTEPS_KEY);
                index = savedInstanceState.getInt(ONSAVEINSTANCESTATE_STEP_INDEX_KEY);
                playerPosition = savedInstanceState.getLong("PLAYERPOSITION");
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

        recipeMediaFragment = new RecipeMediaFragment(playerPosition,path);
        recipeMediaFragment.setRecipeVideo(recipeSteps);
        recipeMediaFragment.setIndex(index);

        recipeStepsFragment = new RecipeStepFragment();
        recipeStepsFragment.setRecipeStep(recipeSteps);
        recipeStepsFragment.setIndex(index);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            counter = findViewById(R.id.tv_steps_counter);
            counter.setText(String.valueOf(index + 1) + "/" + recipeSteps.size());
            next = findViewById(R.id.btn_next);
            previous = findViewById(R.id.btn_prev);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = index < recipeSteps.size() - 1 ? index + 1 : 0;

                    recipeStepsFragment.setIndex(index);
                    recipeStepsFragment.changeFragment();
                    recipeMediaFragment.setIndex(index);
                    recipeMediaFragment.changeFragment();
                    counter.setText(String.valueOf(index + 1) + "/" + recipeSteps.size());
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = index > 0 ? index - 1 : recipeSteps.size() -1;

                    recipeStepsFragment.setIndex(index);
                    recipeStepsFragment.changeFragment();
                    recipeMediaFragment.setIndex(index);
                    recipeMediaFragment.changeFragment();
                    counter.setText(String.valueOf(index + 1) + "/" + recipeSteps.size());
                }
            });
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, recipeStepsFragment).commit();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.media_container, recipeMediaFragment).commit();

    }

    public List<RecipeSteps> getRecipes() {
        return recipeSteps;
    }

    public int getIndex() {
        return index;
    }
}
