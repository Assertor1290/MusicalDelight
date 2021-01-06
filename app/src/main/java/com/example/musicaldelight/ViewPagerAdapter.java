package com.example.musicaldelight;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new Songs();
        } else if (position == 1) {
            return new Artists();
        } else return new Albums();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
