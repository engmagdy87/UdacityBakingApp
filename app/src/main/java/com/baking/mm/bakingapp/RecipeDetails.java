package com.baking.mm.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baking.mm.bakingapp.adapters.RecipeDetailsAdapter;
import com.baking.mm.bakingapp.pojo.RecipeIngredient;
import com.baking.mm.bakingapp.pojo.RecipeSteps;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeDetails extends AppCompatActivity implements RecipeDetailsAdapter.RecipeDetailsAdapterOnClickHandler {
    private RecyclerView recyclerView;
    private String recipeName;
    private List<RecipeSteps> allRecipeSteps;
    List<RecipeIngredient> recipeIngredients;
    private TextView ingredients;
    TextView steps;
    private static final String ONSAVEINSTANCESTATE_RECIPENAME_KEY = "recipeName";
    private static final String ONSAVEINSTANCESTATE_RECIPES_KEY = "recipes";
    private static final String ONSAVEINSTANCESTATE_INGREDIENTS_KEY = "ingredients";
    private RecipeDetailsAdapter recipeAdapter = new RecipeDetailsAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_ingredients_steps);

        ingredients = findViewById(R.id.tv_recipe_ingredients);

        steps = findViewById(R.id.tv_recipe_steps);
        recyclerView = findViewById(R.id.recyclerview_recipes_details);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeAdapter);


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ONSAVEINSTANCESTATE_RECIPENAME_KEY)) {
                recipeName = savedInstanceState.getString(ONSAVEINSTANCESTATE_RECIPENAME_KEY);
                allRecipeSteps = savedInstanceState.getParcelableArrayList(ONSAVEINSTANCESTATE_RECIPES_KEY);
                recipeIngredients = savedInstanceState.getParcelableArrayList(ONSAVEINSTANCESTATE_INGREDIENTS_KEY);

                getSupportActionBar().setTitle(recipeName);
                ingredients.setText(recipeName + " Ingredients");

                recipeAdapter.setRecipe(allRecipeSteps);
            }

        } else {

            Intent myIntent = getIntent();

            Bundle extras = myIntent.getExtras();
            if (extras != null) {
                if (extras.containsKey("name")) {
                    recipeName = myIntent.getStringExtra("name");
                    getSupportActionBar().setTitle(recipeName);
                    ingredients.setText(recipeName + " Ingredients");
                }
                if (extras.containsKey("ingredients")) {
                    recipeIngredients = myIntent.getParcelableArrayListExtra("ingredients");
                }
                if (extras.containsKey("steps")) {
                    allRecipeSteps = myIntent.getParcelableArrayListExtra("steps");
                }
                recipeAdapter.setRecipe(allRecipeSteps);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ONSAVEINSTANCESTATE_RECIPENAME_KEY, recipeName);
        outState.putParcelableArrayList(ONSAVEINSTANCESTATE_RECIPES_KEY, (ArrayList<? extends Parcelable>) allRecipeSteps);
        outState.putParcelableArrayList(ONSAVEINSTANCESTATE_INGREDIENTS_KEY, (ArrayList<? extends Parcelable>) recipeIngredients);
    }

    @Override
    public void onClick(RecipeSteps recipeSteps,int position) {
        Context context = this;
        Class destinationActivity = RecipeStepsNav.class;
        Intent intent = new Intent(context, destinationActivity);

        Bundle extras = new Bundle();
        extras.putString("name",recipeName);
        extras.putInt("clicked_item", position);
        extras.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) allRecipeSteps);

        intent.putExtras(extras);

        startActivity(intent);

    }

    public void onClickIngredients(View view){
        Context context = this;
        Class destinationActivity = RecipeIngredientsList.class;
        Intent intent = new Intent(context, destinationActivity);

        Bundle extras = new Bundle();
        extras.putString("name",recipeName);
        extras.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) recipeIngredients);

        intent.putExtras(extras);

        startActivity(intent);
    }

}
