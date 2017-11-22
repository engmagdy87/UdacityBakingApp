package com.baking.mm.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.javacalsses.RecipeMediaFragment;
import com.baking.mm.bakingapp.pojo.RecipeSteps;

import java.util.List;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsAdapterViewHolder> {

    private List<RecipeSteps> steps;
    private final RecipeDetailsAdapter.RecipeDetailsAdapterOnClickHandler clickHandler;

    public interface RecipeDetailsAdapterOnClickHandler {
        void onClick(RecipeSteps recipeStepsNav, int position);
    }

    public RecipeDetailsAdapter(RecipeDetailsAdapter.RecipeDetailsAdapterOnClickHandler clickHandler){
        this.clickHandler = clickHandler;
    }

    public class RecipeDetailsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView recipeStepsTv;
        public RecipeDetailsAdapterViewHolder(View view){
            super(view);

            recipeStepsTv = view.findViewById(R.id.tv_recipe_steps);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            RecipeSteps recipeSteps = steps.get(adapterPosition);
            clickHandler.onClick(recipeSteps, adapterPosition);
        }
    }


    @Override
    public RecipeDetailsAdapter.RecipeDetailsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForRecipeInList = R.layout.fragment_recipe_step;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(LayoutIdForRecipeInList, parent,false);
        return new RecipeDetailsAdapter.RecipeDetailsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailsAdapter.RecipeDetailsAdapterViewHolder holder, int position) {
        holder.recipeStepsTv.setText(steps.get(position).shortDescription);
    }

    @Override
    public int getItemCount() {
        if(steps == null) return 0;
        return steps.size();
    }

    public void setRecipe(List<RecipeSteps> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }
}

