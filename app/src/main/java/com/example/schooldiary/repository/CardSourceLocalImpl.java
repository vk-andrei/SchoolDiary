package com.example.schooldiary.repository;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.schooldiary.R;

import java.util.ArrayList;
import java.util.List;

public class CardSourceLocalImpl implements CardSource {

    private Resources resources;
    private List<CardData> dataSource;

    public CardSourceLocalImpl(Resources resources) {
        this.resources = resources;
        dataSource = new ArrayList<>(10);
    }

    @SuppressLint("Recycle")
    public CardSourceLocalImpl init() {
        String[] titles = resources.getStringArray(R.array.lessons_names);
        String[] descriptions = resources.getStringArray(R.array.lessons_description);
        TypedArray images = resources.obtainTypedArray(R.array.images);
        Boolean like = false;

        for (int i = 0; i<titles.length; i++) {
            dataSource.add(new CardData(titles[i], descriptions[i], images.getIndex(i), like));
        }
        return this;
    }


    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public List<CardData> getAllCardsData() {
        return dataSource;
    }
}
