package com.example.schooldiary.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.schooldiary.R;
import com.example.schooldiary.publisher.Publisher;

public class MainActivity extends AppCompatActivity {

    private Navigation navigation;
    private Publisher publisher; // - Один паблишер на все приложение (чтобы не было утечек памяти)

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        publisher = new Publisher();
        //FragmentManager fm = getSupportFragmentManager();
        navigation = new Navigation(getSupportFragmentManager());

        if (savedInstanceState == null) {
            DiaryFragment diaryFragment = DiaryFragment.newInstance();

            navigation.replaceFragment(diaryFragment, false);
            //fm.beginTransaction().replace(R.id.container_main, diaryFragment).commit();
        }

    }
}