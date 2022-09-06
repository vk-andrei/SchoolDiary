package com.example.schooldiary.ui;

import static com.example.schooldiary.repository.CardsSourceSharedPrefImpl.KEY_SHARED_PREF_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooldiary.R;
import com.example.schooldiary.publisher.Observer;
import com.example.schooldiary.repository.CardData;
import com.example.schooldiary.repository.CardsSource;
import com.example.schooldiary.repository.CardsSourceLocalImpl;
import com.example.schooldiary.repository.CardsSourceSharedPrefImpl;

import java.util.Calendar;

public class DiaryFragment extends Fragment implements OnMyItemClickListener {

    DiaryAdapter diaryAdapter;
    CardsSource cardsSource;
    RecyclerView recyclerView;

    static final String KEY_SHARED_PREF_1 = "KEY_SHARED_PREF_1";
    static final String KEY_SHARED_PREF_1_CELL_1 = "KEY_SHARED_PREF_1_CELL_1";

    static final int KEY_SOURCE_ARRAY = 1;
    static final int KEY_SOURCE_SHAR_PREF = 2;
    static final int KEY_SOURCE_FIRE_BASE = 3;

    public DiaryFragment() {
        // Required empty public constructor
    }

    public static DiaryFragment newInstance() {
        DiaryFragment fragment = new DiaryFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        *//*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*//*
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSource();
        initRecyclerView(view);
        setHasOptionsMenu(true);
        initRadioGroup(view);
    }

    void setupSource() {
        switch (getCurrentSource()) {
            case KEY_SOURCE_ARRAY:
                cardsSource = new CardsSourceLocalImpl(requireContext().getResources()).init();
                initDiaryAdapter();
                break;
            case KEY_SOURCE_SHAR_PREF:
                cardsSource = new CardsSourceSharedPrefImpl(requireContext().getSharedPreferences(KEY_SHARED_PREF_2, Context.MODE_PRIVATE)).init();
                initDiaryAdapter();
                break;
            case KEY_SOURCE_FIRE_BASE:
                //cardsSource = new CardsSourceFireBaseImpl(requireContext().getResources()).init();
                initDiaryAdapter();
                break;
        }
    }

    private void initRadioGroup(View view) {
        RadioButton rb_array = view.findViewById(R.id.rb_array);
        RadioButton rb_sharPref = view.findViewById(R.id.rb_sharPref);
        RadioButton rb_fireBase = view.findViewById(R.id.rb_fireBase);

        rb_array.setOnClickListener(listener);
        rb_sharPref.setOnClickListener(listener);
        rb_fireBase.setOnClickListener(listener);

        switch (getCurrentSource()) {
            case KEY_SOURCE_ARRAY:
                rb_array.setChecked(true);
                break;
            case KEY_SOURCE_SHAR_PREF:
                rb_sharPref.setChecked(true);
                break;
            case KEY_SOURCE_FIRE_BASE:
                rb_fireBase.setChecked(true);
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rb_array:
                    setCurrentSource(KEY_SOURCE_ARRAY);
                    break;
                case R.id.rb_sharPref:
                    setCurrentSource(KEY_SOURCE_SHAR_PREF);
                    break;
                case R.id.rb_fireBase:
                    setCurrentSource(KEY_SOURCE_FIRE_BASE);
                    break;
            }
            setupSource();
        }
    };

    void setCurrentSource(int currentSource) {
        SharedPreferences sharedPref = requireContext().getSharedPreferences(KEY_SHARED_PREF_1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(KEY_SHARED_PREF_1_CELL_1, currentSource);
        editor.apply();
    }

    int getCurrentSource() {
        SharedPreferences sharedPref = requireContext().getSharedPreferences(KEY_SHARED_PREF_1, Context.MODE_PRIVATE);
        return sharedPref.getInt(KEY_SHARED_PREF_1_CELL_1, KEY_SOURCE_ARRAY);
    }

    private void initDiaryAdapter() {
        if (diaryAdapter == null) {
            diaryAdapter = new DiaryAdapter(this);
        }
        // cardsSource = new CardsSourceLocalImpl(requireContext().getResources()).init();
        diaryAdapter.setData(cardsSource);
        diaryAdapter.setOnMyItemClickListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_lessons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(diaryAdapter);

        /** Незатейливая анимация для начала **/
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(1500);
        animator.setRemoveDuration(1500);
        recyclerView.setItemAnimator(animator);
        /**************************************/

    }

    private String[] getData() {
        String[] lessonNames = getResources().getStringArray(R.array.lessons_names);
        return lessonNames;
    }

    @Override
    public void onMyItemClick(int position) {
        Toast.makeText(requireActivity(), "Item " + getData()[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                cardsSource.addCardData(new CardData("New card" + cardsSource.size(),
                        "New desc" + cardsSource.size(), R.drawable.algebra, false,
                        Calendar.getInstance().getTime()));
                diaryAdapter.notifyItemInserted(cardsSource.size() - 1);
                recyclerView.smoothScrollToPosition(cardsSource.size() - 1);
                return true;
            case R.id.action_clear:
                cardsSource.clearAllCards();
                diaryAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = diaryAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update: {
                // Создадим аноним экземпл класса ЗДЕСЬ И СЕЙЧАС. На месте реализуем метод Обсервера
                // - создаем колбек. Сюда будет приходить ответ от ПАБЛИШЕРА
                Observer observer = new Observer() {
                    @Override
                    public void receivedMessage(CardData cardData) {
                        // получаем ОТВЕТ и ОТПИСЫВАЕМСЯ
                        ((MainActivity) requireActivity()).getPublisher().unSubscribe(this);
                        cardsSource.updateCardData(menuPosition, cardData);
                        diaryAdapter.notifyItemChanged(menuPosition);
                    }
                };
                // ПОДПИСЫВАЕМСЯ
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getNavigation().addFragment(CardEditorFragment.newInstance(cardsSource.getCardData(menuPosition)), true);
                return true;
            }
            case R.id.action_delete: {
                cardsSource.deleteCardData(menuPosition);
                diaryAdapter.notifyItemRemoved(menuPosition);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }
}