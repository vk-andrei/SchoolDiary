package com.example.schooldiary.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schooldiary.R;


public class Navigation {

    private FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private void replaceFragment(Fragment fragment, boolean useBackStack) {
        FragmentTransaction fm = fragmentManager.beginTransaction();
        fm.replace(R.id.container_main, fragment);
        if (useBackStack) {
            fm.addToBackStack("");
        }
        fm.commit();
    }

    private void addFragment(Fragment fragment, boolean useBackStack) {
        FragmentTransaction fm = fragmentManager.beginTransaction();
        fm.add(R.id.container_main, fragment);
        if (useBackStack) {
            fm.addToBackStack("");
        }
        fm.commit();
    }
}
