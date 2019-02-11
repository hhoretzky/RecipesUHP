package com.hhoretzky.recipesuhp;

public class Recipe {
    private String mTitle;
    private String mImage_url;
    private double mSocial_rank;

    public Recipe(String title, String image_url, double social_rank) {
        mTitle=title;
        mImage_url=image_url;
        mSocial_rank=social_rank;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImage_url() {
        return mImage_url;
    }

    public void setmImage_url(String mImage_url) {
        this.mImage_url = mImage_url;
    }

    public double getmSocial_rank() {
        return mSocial_rank;
    }

    public void setmSocial_rank(double mSocial_rank) {
        this.mSocial_rank = mSocial_rank;
    }
}
