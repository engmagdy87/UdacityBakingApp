package com.baking.mm.bakingapp.javacalsses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baking.mm.bakingapp.R;
import com.baking.mm.bakingapp.pojo.RecipeSteps;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by MM on 11/11/2017.
 */

public class RecipeStepFragment extends Fragment {
    public List<RecipeSteps> mRecipeSteps;
    TextView textView;
    public int index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_details, container,false);
        textView = rootView.findViewById(R.id.tv_recipe_step_details);

        if(mRecipeSteps != null){
            textView.setText(mRecipeSteps.get(index).description);
        }
        return rootView;
    }
    public void changeFragment(){
        if(mRecipeSteps != null){
            textView.setText(mRecipeSteps.get(index).description);
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRecipeStep(List<RecipeSteps> mRecipeSteps) {
        this.mRecipeSteps = mRecipeSteps;
    }

}
