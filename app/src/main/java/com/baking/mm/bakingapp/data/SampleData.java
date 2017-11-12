package com.baking.mm.bakingapp.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MM on 11/4/2017.
 */

public class SampleData {
    private static List<String> recipes_names = new ArrayList<String>(){{
        add("Recipe 1");
        add("Recipe 2");
        add("Recipe 3");
        add("Recipe 4");
        add("Recipe 5");
    }};

    private static List<String> ingredients = new ArrayList<String>(){{
        add("3 Eggs");
        add("0.5 Cup of Milk");
        add("Some Sugar");
    }};

    private static List<String> recipe_steps = new ArrayList<String>(){{
        add("Step 1");
        add("Step 2");
        add("Step 3");
    }};

    private static String recipe_step_instruction= "These are the instructions of our recipe";

    public static List<String> getRecipes_names(){
        return recipes_names;
    }
    public static List<String> getIngredients(){
        return ingredients;
    }
    public static List<String> getRecipe_steps(){
        return recipe_steps;
    }
    public static String getRecipe_step_instruction(){
        return recipe_step_instruction;
    }
}
