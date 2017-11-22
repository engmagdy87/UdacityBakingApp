package com.baking.mm.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.baking.mm.bakingapp.adapters.RecipeDetailsAdapter;
import com.baking.mm.bakingapp.adapters.RecipeIngredientsAdapter;
import com.baking.mm.bakingapp.pojo.RecipeIngredient;
import com.baking.mm.bakingapp.widget.UpdateBakingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MM on 11/15/2017.
 */

public class RecipeIngredientsList extends AppCompatActivity {
    String recipeName;
    List<RecipeIngredient> recipeIngredients;
    RecyclerView recyclerView;
    ArrayList<String> listIngStr = null;
    private static final String ONSAVEINSTANCESTATE_RECIPENAME_KEY = "recipeName";
    private static final String ONSAVEINSTANCESTATE_INGREDIENTS_KEY = "ingredients";

    private RecipeIngredientsAdapter ingredientAdapter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ONSAVEINSTANCESTATE_RECIPENAME_KEY,recipeName);
        outState.putParcelableArrayList(ONSAVEINSTANCESTATE_INGREDIENTS_KEY, (ArrayList<? extends Parcelable>) recipeIngredients);
        outState.putStringArrayList("TEMPLIST", listIngStr);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ingredients_list);

        ingredientAdapter = new RecipeIngredientsAdapter();

        recyclerView = findViewById(R.id.recyclerview_recipes_Ingredients_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ONSAVEINSTANCESTATE_RECIPENAME_KEY)) {
                recipeName = savedInstanceState.getString(ONSAVEINSTANCESTATE_RECIPENAME_KEY);
                recipeIngredients = savedInstanceState.getParcelableArrayList(ONSAVEINSTANCESTATE_INGREDIENTS_KEY);
                listIngStr = savedInstanceState.getStringArrayList("TEMPLIST");
                getSupportActionBar().setTitle(recipeName);
                recyclerView.setAdapter(ingredientAdapter);
                ingredientAdapter.setIngredients(recipeIngredients);
                UpdateBakingService.startBakingService(this,listIngStr);
            }

        } else {
        Intent myIntent = getIntent();

        Bundle extras = myIntent.getExtras();
        if (extras != null) {
            if (extras.containsKey("name")) {
                recipeName = myIntent.getStringExtra("name");
                getSupportActionBar().setTitle(recipeName);

            }
            if (extras.containsKey("ingredients")) {
                recipeIngredients = myIntent.getParcelableArrayListExtra("ingredients");
            }
            recyclerView.setAdapter(ingredientAdapter);
            ingredientAdapter.setIngredients(recipeIngredients);
            listIngStr = new ArrayList<String>(recipeIngredients.size());
            UpdateBakingService.startBakingService(this,listIngStr);
        }

    }
    }
}
