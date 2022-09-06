package com.example.schooldiary.repository.remote;

import com.example.schooldiary.repository.CardsSource;

public interface RemoteFireStoreResponse {

    // он пробрасывает ответ, что fireStore инициализировался и получил коллекцию

    void initialized(CardsSource cardsSource);

}
