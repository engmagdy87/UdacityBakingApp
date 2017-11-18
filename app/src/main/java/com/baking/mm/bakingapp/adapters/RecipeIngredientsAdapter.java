package com.baking.mm.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.pojo.RecipeIngredient;

import java.util.List;

/**
 * Created by MM on 11/15/2017.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsAdapterViewHolder> {
    private List<RecipeIngredient> ingredients;


    public RecipeIngredientsAdapter() {

    }



    @Override
    public RecipeIngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForRecipeInList = R.layout.recipe_ingredients_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(LayoutIdForRecipeInList, parent,false);
        return new RecipeIngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeIngredientsAdapterViewHolder holder, int position) {

        holder.ingredientTv.setText(ingredients.get(position).quantity + " " + ingredients.get(position).measure + " " + ingredients.get(position).ingredient);
    }

    @Override
    public int getItemCount() {
        if(ingredients == null) return 0;
        return ingredients.size();
    }

    public class RecipeIngredientsAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView ingredientTv;
        public RecipeIngredientsAdapterViewHolder(View view) {
            super(view);
            ingredientTv = view.findViewById(R.id.tv_recipe_ingredients_list);

        }


    }
    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
}
