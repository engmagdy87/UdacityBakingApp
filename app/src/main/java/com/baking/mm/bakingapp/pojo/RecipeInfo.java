package com.baking.mm.bakingapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by MM on 11/6/2017.
 */

public class RecipeInfo implements Parcelable {
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


    protected RecipeInfo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        ingredients = in.createTypedArrayList(RecipeIngredient.CREATOR);
        steps = in.createTypedArrayList(RecipeSteps.CREATOR);
        if (in.readByte() == 0) {
            servings = null;
        } else {
            servings = in.readInt();
        }
        image = in.readString();
    }

    public static final Creator<RecipeInfo> CREATOR = new Creator<RecipeInfo>() {
        @Override
        public RecipeInfo createFromParcel(Parcel in) {
            return new RecipeInfo(in);
        }

        @Override
        public RecipeInfo[] newArray(int size) {
            return new RecipeInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        if (servings == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(servings);
        }
        parcel.writeString(image);
    }
}
