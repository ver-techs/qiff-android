package com.ver_techs.qiff_android.custom_adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.ver_techs.qiff_android.fragments.AboutFragment;
import com.ver_techs.qiff_android.fragments.PointsTableFragment;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.fragments.FixtureFragment;
import com.ver_techs.qiff_android.fragments.HomeFragment;

//Adapter for tabs
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    private int[] imageResId = { //Icons of tabs in order
            R.drawable.icon_calendar,
            R.drawable.icon_home,
            R.drawable.icon_points_table,
            R.drawable.icon_about
    };
    Context context;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(Context context, FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.context=context;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            FixtureFragment tab2 = new FixtureFragment();
            return tab2;
        }
        else if(position == 1)
        {
            HomeFragment tab3 = new HomeFragment();
            return tab3;
        }
        else if(position == 2)
        {
            PointsTableFragment tab4 = new PointsTableFragment();
            return tab4;
        }
        else
        {
            AboutFragment tab1 = new AboutFragment();
            return tab1;
        }

    }


    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {

        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM); //set the image onto the tab
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

}