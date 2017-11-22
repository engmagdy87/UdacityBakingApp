package com.baking.mm.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baking.mm.bakingapp.IdlingResource.SimpleIdlingResource;
import com.baking.mm.bakingapp.adapters.RecipeCardAdapter;
import com.baking.mm.bakingapp.pojo.RecipeInfo;
import com.baking.mm.bakingapp.networkhelpers.NetworkUtils;
import com.baking.mm.bakingapp.IdlingResource.SimpleIdlingResource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler, Callback<ArrayList<RecipeInfo>> {
    private ArrayList<RecipeInfo> mRecipeInfo = null;
    private RecyclerView recyclerView;
    private ProgressBar loadingIndicator;
    private static final String ONSAVEINSTANCESTATE_RECIPES_KEY = "recipes";
    SimpleIdlingResource idlingResource = (SimpleIdlingResource)getIdlingResource();

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Nullable
    private SimpleIdlingResource mIdlingResource;

    private RecipeCardAdapter recipeAdapter = new RecipeCardAdapter(this, this);


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ONSAVEINSTANCESTATE_RECIPES_KEY, mRecipeInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (findViewById(R.id.fragment_recipes_list_two_panes) != null) {
            recyclerView = findViewById(R.id.recyclerview_recipes);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
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

        } else {
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
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        getIdlingResource();
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
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeInfo>> call, Throwable t) {
                recyclerView.setVisibility(View.INVISIBLE);
                loadingIndicator.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "An internet connection error. Please try again",
                        Toast.LENGTH_LONG).show();
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
