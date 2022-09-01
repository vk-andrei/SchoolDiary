package com.example.schooldiary.repository;

import java.util.List;

public interface CardSource {

    int size();

    CardData getCardData(int position);

    List<CardData> getAllCardsData();

}
