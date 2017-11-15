package com.baking.mm.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baking.mm.bakingapp.adapters.RecipeDetailsAdapter;
import com.baking.mm.bakingapp.adapters.RecipeIngredientsAdapter;
import com.baking.mm.bakingapp.pojo.RecipeIngredient;

import java.util.List;

/**
 * Created by MM on 11/15/2017.
 */

public class RecipeIngredientsList extends AppCompatActivity {
    String recipeName;
    List<RecipeIngredient> recipeIngredients;
    RecyclerView recyclerView;

    private RecipeIngredientsAdapter ingredientAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ingredients_list);

        ingredientAdapter = new RecipeIngredientsAdapter();
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

            recyclerView = findViewById(R.id.recyclerview_recipes_Ingredients_list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(ingredientAdapter);
            ingredientAdapter.setIngredients(recipeIngredients);
        }


    }
}
