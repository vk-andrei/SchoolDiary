package com.example.schooldiary.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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

    private DatePicker dp_inputDate;
    private EditText et_title;
    private EditText et_desc;

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
            initView(view);
            setContent();
            setListeners(view);
        }
    }

    private void initView(View view) {
        et_title = view.findViewById(R.id.et_title);
        et_desc = view.findViewById(R.id.et_description);
        dp_inputDate = view.findViewById(R.id.dp_inputDate);
    }

    private void setContent() {
        cardData = getArguments().getParcelable("cardData");
        et_title.setText(cardData.getTitle());
        et_desc.setText(cardData.getDescription());

        calendar = Calendar.getInstance();
        calendar.setTime(cardData.getDate());

        dp_inputDate.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    private void setListeners(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dp_inputDate.setOnDateChangedListener((view1, year, monthOfYear, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            });
        }

        view.findViewById(R.id.btn_save).setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                DatePicker datePicker = ((DatePicker) view.findViewById(R.id.dp_inputDate));
                calendar.set(Calendar.YEAR, datePicker.getYear());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            }
            save();
        });

        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }

    private void save() {
        cardData.setDate(calendar.getTime());
        cardData.setTitle(et_title.getText().toString());
        cardData.setDescription(et_desc.getText().toString());

        ((MainActivity) requireActivity()).getPublisher().sendMessage(cardData);
        ((MainActivity) requireActivity()).getSupportFragmentManager().popBackStack();
    }
}