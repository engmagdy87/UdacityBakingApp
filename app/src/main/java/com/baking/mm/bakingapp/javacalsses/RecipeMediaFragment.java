package com.baking.mm.bakingapp.javacalsses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.pojo.RecipeSteps;

import java.util.List;

/**
 * Created by MM on 11/8/2017.
 */

public class RecipeMediaFragment extends Fragment {
    public List<RecipeSteps> mRecipeSteps;
    TextView textView;
    public int index;

    public RecipeMediaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media, container, false);
        textView = rootView.findViewById(R.id.tv_recipe_video);
        if (mRecipeSteps != null) {
            if (mRecipeSteps.get(index).videoURL.toString().isEmpty() || mRecipeSteps.get(index).videoURL.toString().equals(""))
                textView.setText("No Video");
            else
                textView.setText(mRecipeSteps.get(0).videoURL.toString());
        }
        return rootView;

    }

    public void changeFragment() {
        if (mRecipeSteps != null) {
            if (mRecipeSteps.get(index).videoURL.toString().isEmpty() || mRecipeSteps.get(index).videoURL.toString().equals(""))
                textView.setText("No Video");
            else
                textView.setText(mRecipeSteps.get(index).videoURL.toString());

        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRecipeVideo(List<RecipeSteps> mRecipeSteps) {
        this.mRecipeSteps = mRecipeSteps;
    }


}
