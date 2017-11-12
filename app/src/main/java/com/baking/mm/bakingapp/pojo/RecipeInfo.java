package com.baking.mm.bakingapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by MM on 11/6/2017.
 */

public class RecipeInfo  {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("ingredients")
    @Expose
    public ArrayList<RecipeIngredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    public ArrayList<RecipeSteps> steps = null;
    @SerializedName("servings")
    @Expose
    public Integer servings;
    @SerializedName("image")
    @Expose
    public String image;
}
