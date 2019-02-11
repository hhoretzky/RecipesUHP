package com.hhoretzky.recipesuhp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.RecoverySystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RelativeLayout layout;

    private static final String RECIPES_URL="https://www.food2fork.com/api/search/?key=291b7e7495709e9263fc1df6f88b0612";
    private RecipeAdapter mAdapter;
    public static final String LOG_TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar1);

        ListView recipeListView = (ListView) findViewById(R.id.list);
        mAdapter = new RecipeAdapter(this, new ArrayList<Recipe>());
        recipeListView.setAdapter(mAdapter);

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recipe currentRecipe = mAdapter.getItem(postition);
                //redirect to the recipe
            }
        });
    RecipeAsyncTask task = new RecipeAsyncTask();
    task.execute(RECIPES_URL);
    }

    @SuppressLint("StaticFieldLeak")
    private  class RecipeAsyncTask extends AsyncTask<String,Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            return QueryUtils.fetchRecipeData(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Recipe> data) {
            progressBar.setVisibility(View.GONE);
            mAdapter.clear();
            if (data !=null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }

        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }
    }
}
