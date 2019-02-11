package com.hhoretzky.recipesuhp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.hhoretzky.recipesuhp.MainActivity.LOG_TAG;


public final class QueryUtils {
    private QueryUtils() {
    }

    public static List<Recipe> extractFeatureFromJson(String recipeJSON) {

        if(TextUtils.isEmpty(recipeJSON)) {
            return null;
        }

        List<Recipe> recipes = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(recipeJSON);
            JSONArray recipeArray = baseJsonResponse.getJSONArray("recipes");
            for (int i=0; i<=recipeArray.length();i++){
                JSONObject currentRecipe = recipeArray.getJSONObject(i);
                String title = currentRecipe.getString("title");

                Log.i("+++++++", title);
                double rank = currentRecipe.getDouble("social_rank");
                String image_url = currentRecipe.getString("image_url");

                Recipe recipe = new Recipe(title,image_url,rank);
                recipes.add(recipe);


            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return recipes;
    }

    public static List<Recipe> fetchRecipeData (String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }
        List<Recipe> recipes = extractFeatureFromJson(jsonResponse);
        return recipes;
    }



    public static URL createURL(String stringUrl){ URL url=null;
        try {
            url=new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "problem building the URL",e);
        }
        return url;

    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection =(HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results");
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
