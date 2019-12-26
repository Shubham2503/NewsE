package com.innovative_coder.news.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.innovative_coder.news.Fragment.CategoryFragment;
import com.innovative_coder.news.Fragment.SourcesFragment;
import com.innovative_coder.news.Fragment.TrendingFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private Context contex;

    public MyFragmentAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.contex = context;


    }



    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return CategoryFragment.getInstance();
        else if(position == 1)
            return TrendingFragment.getInstance();
        else if (position == 2)
            return SourcesFragment.getInstance();
        else
            return null;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Category";
            case 1:
                return "Trending";
            case 2:
                return "Sources";
        }
        return "";
    }
}
