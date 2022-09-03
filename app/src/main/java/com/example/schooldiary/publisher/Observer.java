package com.example.schooldiary.publisher;

import com.example.schooldiary.repository.CardData;

/**
 * Любой observer ОБЯЗАН уметь принимать сообщения типа (CardData cardData)!!
 **/
public interface Observer {
    void receivedMessage(CardData cardData);
}
