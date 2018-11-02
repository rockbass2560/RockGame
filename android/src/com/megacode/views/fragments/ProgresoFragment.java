package com.megacode.views.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.megacode.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgresoFragment extends Fragment {

    private final static int PAGE_LIMIT = 2;
    private TabLayout tabLayout;

    public ProgresoFragment() {
        // Required empty public constructor
    }

    class TabAdapter extends FragmentPagerAdapter{

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence title = null;

            switch (position){
                case 0:
                    title = "Niveles";
                    break;
                case 1:
                    title = "Puntaciones";
                    break;
            }

            return title;
        }

        @Override
        public Fragment getItem(int index) {
            Fragment fragment = null;

            switch (index){
                case 0:
                        fragment = new SkillTree();
                    break;
                case 1:
                        fragment = new ScoreFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_LIMIT;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progreso, container, false);

        ViewPager viewPager = view.findViewById(R.id.progreso_pager);
        tabLayout = view.findViewById(R.id.tab_progreso);

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}