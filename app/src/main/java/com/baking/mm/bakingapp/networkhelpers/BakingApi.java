package com.baking.mm.bakingapp.networkhelpers;

import com.baking.mm.bakingapp.pojo.RecipeInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MM on 11/9/2017.
 */

public interface BakingApi {
    @GET("baking.json")
    Call<ArrayList<RecipeInfo>> getRecipes();
}
