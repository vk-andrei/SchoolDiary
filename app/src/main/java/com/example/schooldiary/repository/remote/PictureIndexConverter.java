package com.example.schooldiary.repository.remote;

import com.example.schooldiary.R;

import java.util.Random;

public class PictureIndexConverter {

    private static Random random = new Random();

    private static int[] picIndex = {
            R.drawable.algebra,
            R.drawable.english,
            R.drawable.fizra,
            R.drawable.history,
            R.drawable.literature,
            R.drawable.painting,
            R.drawable.russian
    };

    public static int randomPictureIndex() {
        return random.nextInt(picIndex.length);
    }

    public static int getPictureByIndex(int index) {
        if (index < 0 || index > picIndex.length) {
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture) {
        for (int i = 0; i < picIndex.length; i++) {
            if (picture == picIndex[i]) {
                return i;
            }
        }
        return picIndex[0];
    }
}