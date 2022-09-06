package com.example.schooldiary.repository.remote;

import com.example.schooldiary.repository.CardData;
import com.google.firebase.Timestamp;

import java.util.Map;
import java.util.HashMap;


public class CardDataMapping {

    public static class Fields {
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String IMAGE = "image";
        public final static String LIKE = "like";
        public final static String DATE = "date";
    }

    // перевод из ДОКУМЕНТА (из FireBase) в КАРТОЧКУ
    public static CardData toCardData(String id, Map<String, Object> doc) {
        long indexPic = (long) doc.get(Fields.IMAGE);
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        CardData answer =
                new CardData((String) doc.get(Fields.TITLE),
                        (String) doc.get(Fields.DESCRIPTION),
                        PictureIndexConverter.getPictureByIndex((int) indexPic),
                        (boolean) doc.get(Fields.LIKE),
                        timeStamp.toDate());

        answer.setId(id);
        return answer;
    }

    // перевод из КАРТОЧКИ в ДОКУМЕНТ для FireBase
    public static Map<String, Object> toDocument(CardData cardData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.DESCRIPTION, cardData.getDescription());
        answer.put(Fields.IMAGE, PictureIndexConverter.getIndexByPicture(cardData.getImage()));
        answer.put(Fields.LIKE, cardData.isLike());
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }
}