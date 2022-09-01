package com.example.schooldiary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooldiary.R;

public class DiaryFragment extends Fragment implements OnMyItemClickListener {

    DiaryAdapter diaryAdapter;


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

        initDiaryAdapter();
        initRecyclerView(view);
    }

    private void initDiaryAdapter() {
        diaryAdapter = new DiaryAdapter();
        diaryAdapter.setData(getData());
        diaryAdapter.setOnMyItemClickListener(this);
    }
    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_lessons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(diaryAdapter);
    }

    private String[] getData() {
        String[] lessonNames = getResources().getStringArray(R.array.lessons_names);
        return lessonNames;
    }


    @Override
    public void onMyItemClick(int position) {
        Toast.makeText(requireActivity(), "Item " + getData()[position], Toast.LENGTH_SHORT).show();
    }
}