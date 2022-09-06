package com.example.schooldiary.repository;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CardsSourceSharedPrefImpl implements CardsSource {

    private List<CardData> dataSource;
    private SharedPreferences sharedPref;

    public static final String KEY_SHARED_PREF_2 = "KEY_SHARED_PREF_2";
    static final String KEY_SHARED_PREF_2_CELL_1 = "KEY_SHARED_PREF_2_CELL_1";

    public CardsSourceSharedPrefImpl(SharedPreferences sharedPref) {
        dataSource = new ArrayList<CardData>();
        this.sharedPref = sharedPref;
    }

    public CardsSourceSharedPrefImpl init() {
        /** извлекаем из ШаредПреф наш объект **/
        // ОДНУ КАРТОЧКУ
        // String savedCard = sharedPref.getString(KEY_SHARED_PREF_2_CELL_1, null);
        // if (savedCard != null) {
        //          dataSource.add(new GsonBuilder().create().fromJson(savedCard, CardData.class));

        // ВЕСЬ СПИСОК КАРТОЧЕК
        String savedListCards = sharedPref.getString(KEY_SHARED_PREF_2_CELL_1, null);
        if (savedListCards != null) {
            // Извлекаем весь список карточек:
            Type type = new TypeToken<ArrayList<CardData>>() {
            }.getType();
            dataSource = new GsonBuilder().create().fromJson(savedListCards, type);
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

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
        /** кладем в ШаредПреф наш объект **/
        SharedPreferences.Editor editor = sharedPref.edit();
        // когда клали одну карточку:
        // editor.putString(KEY_SHARED_PREF_2_CELL_1, new GsonBuilder().create().toJson(cardData));
        // Будем класть вместо одной карточки весь dataSource
        editor.putString(KEY_SHARED_PREF_2_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void clearAllCards() {
        dataSource.clear();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_SHARED_PREF_2_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_SHARED_PREF_2_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_SHARED_PREF_2_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }
}