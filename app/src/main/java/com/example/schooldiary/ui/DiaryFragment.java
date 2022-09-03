package com.example.schooldiary.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooldiary.R;
import com.example.schooldiary.repository.CardData;
import com.example.schooldiary.repository.CardsSource;
import com.example.schooldiary.repository.CardsSourceLocalImpl;

public class DiaryFragment extends Fragment implements OnMyItemClickListener {

    DiaryAdapter diaryAdapter;
    CardsSource cardsSource;
    RecyclerView recyclerView;

    public DiaryFragment() {
        // Required empty public constructor
    }

    public static DiaryFragment newInstance() {
        DiaryFragment fragment = new DiaryFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardsSource = new CardsSourceLocalImpl(requireContext().getResources()).init();
        initDiaryAdapter();
        initRecyclerView(view);
        setHasOptionsMenu(true);
    }

    private void initDiaryAdapter() {
        diaryAdapter = new DiaryAdapter(this);
        diaryAdapter.setData(cardsSource);
        diaryAdapter.setOnMyItemClickListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_lessons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(diaryAdapter);

        /** Незатейливая анимация для начала **/
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(5000);
        animator.setRemoveDuration(5000);
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
                        "New desc" + cardsSource.size(), R.drawable.algebra, false));
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
        requireActivity().getMenuInflater().inflate(R.menu.card_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = diaryAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                cardsSource.updateCardData(menuPosition, new CardData("Upd card" + cardsSource.size(),
                        "Upd desc" + cardsSource.size(), cardsSource.getCardData(menuPosition).getImage(), false));
                diaryAdapter.notifyItemChanged(menuPosition);
                return true;
            case R.id.action_delete:
                cardsSource.deleteCardData(menuPosition);
                diaryAdapter.notifyItemRemoved(menuPosition);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}