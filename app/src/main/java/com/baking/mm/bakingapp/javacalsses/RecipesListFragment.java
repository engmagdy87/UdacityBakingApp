package com.baking.mm.bakingapp.javacalsses;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.adapters.RecipeCardAdapter;
import com.baking.mm.bakingapp.data.SampleData;

/**
 * Created by MM on 11/6/2017.
 */

public class RecipesListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipes_lists,container,false);
        return rootView;
    }
}
