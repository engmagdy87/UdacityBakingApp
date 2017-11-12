package com.baking.mm.bakingapp.javacalsses;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.mm.bakingapp.R;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeIngredientsStepsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recipe_ingredients_steps,container,false);
        return rootView;
    }
}
