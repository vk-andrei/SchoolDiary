package com.example.schooldiary.publisher;

import com.example.schooldiary.repository.CardData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    /** Лист наших подписчиков **/
    private List<Observer> observers;

    Publisher() {
        observers = new ArrayList<>();
    }

    /** Подписываем и отписываем подписчиков **/
    private void subscribe(Observer observer) {
        observers.add(observer);
    }
    private void unSubscribe(Observer observer) {
        observers.remove(observer);
    }

    /** Разослать всем подписчикам обновленную информацию **/
    public void sendMessage(CardData cardData){
        for (Observer observer: observers) {
            observer.receivedMessage(cardData);
        }
    }
}