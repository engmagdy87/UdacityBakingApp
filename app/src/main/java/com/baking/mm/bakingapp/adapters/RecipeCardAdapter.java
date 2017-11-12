package com.baking.mm.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.pojo.RecipeInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MM on 11/6/2017.
 */

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeCardAdapterViewHolder> {

    private List<RecipeInfo> recipesData;
    private Context context;

    private final RecipeCardAdapterOnClickHandler clickHandler;

    public interface RecipeCardAdapterOnClickHandler {
        void onClick(RecipeInfo recipeInfo);
    }

    public RecipeCardAdapter(Context context, RecipeCardAdapter.RecipeCardAdapterOnClickHandler clickHandler){
        this.context = context;
        this.clickHandler = clickHandler;
    }

    public class RecipeCardAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView recipeTv;
        public final ImageView recipeImg;
        public RecipeCardAdapterViewHolder(View view){
            super(view);
            recipeTv = (TextView) view.findViewById(R.id.tv_recipe_name);
            recipeImg = (ImageView) view.findViewById(R.id.iv_recipe_img);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            RecipeInfo recipeInfo = recipesData.get(adapterPosition);
            clickHandler.onClick(recipeInfo);
        }
    }


    @Override
    public RecipeCardAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForRecipeInList = R.layout.recipe_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(LayoutIdForRecipeInList, parent,false);
        return new RecipeCardAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeCardAdapterViewHolder holder, int position) {
        holder.recipeTv.setText(recipesData.get(position).name);
        if(recipesData.get(position).image.equals("")){
            holder.recipeImg.setImageResource(R.drawable.baking);
        } else{
            Picasso.with(context).load(recipesData.get(position).image).into(holder.recipeImg);
        }

    }

    @Override
    public int getItemCount() {
        if(recipesData == null) return 0;
        return recipesData.size();
    }

    public void setRecipe(List<RecipeInfo> recipe) {
        recipesData = recipe;
        notifyDataSetChanged();
    }
}
