package com.example.schooldiary.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;
import com.example.schooldiary.repository.CardData;

import java.util.Calendar;
import java.util.Date;

public class CardEditorFragment extends Fragment {

    private CardData cardData;
    private Calendar calendar;

    public CardEditorFragment() {
        // Required empty public constructor
    }

    public static CardEditorFragment newInstance(CardData cardData) {
        CardEditorFragment fragment = new CardEditorFragment();
        Bundle args = new Bundle();
        args.putParcelable("cardData", cardData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //container.removeAllViews();
        return inflater.inflate(R.layout.fragment_card_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            cardData = getArguments().getParcelable("cardData");
            ((EditText) view.findViewById(R.id.et_title)).setText(cardData.getTitle());
            ((EditText) view.findViewById(R.id.et_description)).setText(cardData.getDescription());

            calendar = Calendar.getInstance();
            calendar.setTime(cardData.getDate());

            ((DatePicker) view.findViewById(R.id.dp_inputDate)).init(calendar.get(Calendar.YEAR) - 1,
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((DatePicker) view.findViewById(R.id.dp_inputDate)).setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }
                });

                view.findViewById(R.id.btn_save).setOnClickListener(v -> {
                    cardData.setTitle(((EditText) view.findViewById(R.id.et_title)).getText().toString());
                    cardData.setDescription(((EditText) view.findViewById(R.id.et_description)).getText().toString());

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        DatePicker datePicker = ((DatePicker) view.findViewById(R.id.dp_inputDate));
                        calendar.set(Calendar.YEAR, datePicker.getYear());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    }

                    cardData.setDate(calendar.getTime());

                    ((MainActivity) requireActivity()).getPublisher().sendMessage(cardData);
                    ((MainActivity) requireActivity()).getSupportFragmentManager().popBackStack();
                });
            }
        }

    }
}