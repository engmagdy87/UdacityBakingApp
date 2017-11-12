package com.baking.mm.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
    Button next;
    Button previous;
    TextView counter;
    int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_browsing);


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
                index = myIntent.getIntExtra("clicked_item",0);
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
