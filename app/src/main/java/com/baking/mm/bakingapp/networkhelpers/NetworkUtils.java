package com.baking.mm.bakingapp.networkhelpers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MM on 10/7/2017.
 */

public class NetworkUtils {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public BakingApi bakingApi;

    public NetworkUtils() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bakingApi = retrofit.create(BakingApi.class);

    }

    public BakingApi getBakingApi() {
        return bakingApi;
    }


}
