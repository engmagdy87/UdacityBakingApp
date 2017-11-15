package com.baking.mm.bakingapp.javacalsses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.mm.bakingapp.R;

/**
 * Created by MM on 11/15/2017.
 */

public class RecipeIngredientsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_list, container,false);
        return rootView;
    }
}
