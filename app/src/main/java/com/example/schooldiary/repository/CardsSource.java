package com.example.schooldiary.repository;

import java.util.List;

public interface CardsSource {

    int size();

    CardData getCardData(int position);

    List<CardData> getAllCardsData();

    // for options menu...
    void addCardData(CardData cardData);

    void clearAllCards();

    // for context menu
    void updateCardData(int position, CardData newCardData);

    void deleteCardData(int position);

}
