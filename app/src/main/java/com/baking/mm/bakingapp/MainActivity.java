package com.baking.mm.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.baking.mm.bakingapp.adapters.RecipeCardAdapter;
import com.baking.mm.bakingapp.pojo.RecipeInfo;
import com.baking.mm.bakingapp.networkhelpers.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler, Callback<ArrayList<RecipeInfo>> {
    private ArrayList<RecipeInfo> mRecipeInfo = null;
    private RecyclerView recyclerView;
    private ProgressBar loadingIndicator;
    private static final String ONSAVEINSTANCESTATE_RECIPES_KEY = "recipes";

    private RecipeCardAdapter recipeAdapter = new RecipeCardAdapter(this, this);

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ONSAVEINSTANCESTATE_RECIPES_KEY,mRecipeInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview_recipes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeAdapter);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ONSAVEINSTANCESTATE_RECIPES_KEY)) {
                mRecipeInfo = savedInstanceState.getParcelableArrayList(ONSAVEINSTANCESTATE_RECIPES_KEY);
                recipeAdapter.setRecipe(mRecipeInfo);
            }

        } else {
        getRecipes();
        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
    }
    }

    private void getRecipes() {
        final Call<ArrayList<RecipeInfo>> recipeInfo = new NetworkUtils().getBakingApi().getRecipes();

        recipeInfo.enqueue(new Callback<ArrayList<RecipeInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeInfo>> call, Response<ArrayList<RecipeInfo>> response) {

                if (response != null) {
                    ArrayList<RecipeInfo> recipeInfo = response.body();
                    mRecipeInfo = recipeInfo;
                    recipeAdapter.setRecipe(recipeInfo);
                    loadingIndicator.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeInfo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(RecipeInfo recipeInfo) {
        Context context = this;
        Class destinationActivity = RecipeDetails.class;
        Intent intent = new Intent(context, destinationActivity);

        Bundle extras = new Bundle();

        extras.putString("name", recipeInfo.name);
        extras.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) recipeInfo.ingredients);
        extras.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) recipeInfo.steps);

        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<ArrayList<RecipeInfo>> call, Response<ArrayList<RecipeInfo>> response) {

    }

    @Override
    public void onFailure(Call<ArrayList<RecipeInfo>> call, Throwable t) {

    }
}
