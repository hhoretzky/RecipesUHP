package com.hhoretzky.recipesuhp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    public RecipeAdapter (Context context, List<Recipe> recipes) {
        super(context,0,recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item,parent,false);
        }
        Recipe currentRecipe = getItem(position);

        //magnitude



        ImageView image = (ImageView) listItemView.findViewById(R.id.picture_list);
        String image_url = currentRecipe.getmImage_url();
        Picasso.get().load(image_url).into(image);
        TextView TVtitle = (TextView) listItemView.findViewById(R.id.text_list);
        String title = currentRecipe.getmTitle();
        TVtitle.setText(title);
        TextView rank = (TextView)listItemView.findViewById(R.id.rank_list);
        double mRank=currentRecipe.getmSocial_rank();
        rank.setText(String.valueOf(mRank));


        return listItemView;
    }


}



