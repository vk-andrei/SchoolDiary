package com.example.schooldiary.repository.remote;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.schooldiary.repository.CardData;
import com.example.schooldiary.repository.CardsSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class CardsSourceRemoteImpl implements CardsSource {

    private List<CardData> dataSource;

    private static final String CARDS_COLLECTIONS = "cards";

    // 1. Создаем удаленный экземпляр нашей удаленной БД
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    // 2. Создаем ссылку на коллекцию документов внутри БД
    CollectionReference collectionReference = firebaseFirestore.collection(CARDS_COLLECTIONS);


    public CardsSourceRemoteImpl() {
        dataSource = new ArrayList<CardData>();
    }

    public CardsSourceRemoteImpl init(RemoteFireStoreResponse remoteFireStoreResponse) {
        // Запрашиваем все записи из нашей коллекции (sort by DATE, ascending)
        collectionReference.orderBy(CardDataMapping.Fields.DATE, Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // если запрос прошел и все хорошо, обнуляем наш dataSource и пройдемся циклом по
                    // всем документам которые к нам пришли:
                    dataSource = new ArrayList<CardData>();
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Map<String, Object> document = queryDocumentSnapshot.getData(); // та самая наша карта здесь!
                        String id = queryDocumentSnapshot.getId();
                        CardData addingCard = CardDataMapping.toCardData(id, document);
                        dataSource.add(addingCard);
                    }
                }
                // Сделали колбек, чтобы передать ОТВЕТ С СЕРВЕРА, как только он будет готов
                remoteFireStoreResponse.initialized(CardsSourceRemoteImpl.this);
            }
        });
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

        //collectionReference.add(CardDataMapping.toDocument(cardData));
        // Можно так как выше, а можно и так, но не обязательно
        collectionReference.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearAllCards() {

        for (CardData cardData: dataSource) {
            collectionReference.document(cardData.getId()).delete();
        }

        dataSource.clear();
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);

        String id = newCardData.getId();
        collectionReference.document(id).set(CardDataMapping.toDocument(newCardData));

    }

    @Override
    public void deleteCardData(int position) {
        collectionReference.document(dataSource.get(position).getId()).delete();
        dataSource.remove(position);
    }
}