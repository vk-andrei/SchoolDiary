package com.example.schooldiary.repository;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.schooldiary.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardsSourceLocalImpl implements CardsSource {

    private Resources resources;
    private List<CardData> dataSource;

    public CardsSourceLocalImpl(Resources resources) {
        this.resources = resources;
        dataSource = new ArrayList<>(10);
    }

    @SuppressLint("Recycle")
    public CardsSourceLocalImpl init() {
        String[] titles = resources.getStringArray(R.array.lessons_names);
        String[] descriptions = resources.getStringArray(R.array.lessons_description);
        TypedArray images = resources.obtainTypedArray(R.array.images);

        Boolean like = false;

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardData(titles[i], descriptions[i], images.getResourceId(i, 0), false, Calendar.getInstance().getTime()));
        }
        return this;
    }


    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public CardData getCardData(int position) {
//        if (position>6) {
//            position = position%7;
//        }
        return dataSource.get(position);
    }

    @Override
    public List<CardData> getAllCardsData() {
        return dataSource;
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearAllCards() {
        dataSource.clear();
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }
}
