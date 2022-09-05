package com.example.schooldiary.ui;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schooldiary.R;

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment fragment, boolean useBackStack) {
        FragmentTransaction fm = fragmentManager.beginTransaction();
        fm.replace(R.id.container_main, fragment);
        if (useBackStack) {
            fm.addToBackStack("");
        }
        fm.commit();
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {
        FragmentTransaction fm = fragmentManager.beginTransaction();
        fm.add(R.id.container_main, fragment);
        //fm.add(         (ViewGroup) getView().getParent()).getId()        , fragment);
        if (useBackStack) {
            fm.addToBackStack("");
        }
        fm.commit();
    }
}