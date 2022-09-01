package com.example.schooldiary.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooldiary.R;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

    private String[] data;
    private OnMyItemClickListener onMyItemClickListener;

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }

    // Можно так (и лучше так)
    public void setData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }
    // а можно и в конструктор
    public DiaryAdapter(String[] data) {
        this.data = data;
    }

    public DiaryAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_diary_recycler_cardview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindContentWithLayout(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLessonName;
        private TextView tvLessonDescription;
        private ImageView imgLesson;
        private CheckBox chkLesson;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tv_lesson_name);
            tvLessonDescription = itemView.findViewById(R.id.tv_lesson_description);
            imgLesson = itemView.findViewById(R.id.img_lesson_image);
            chkLesson = itemView.findViewById(R.id.chk_lesson);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMyItemClickListener != null) {
                        onMyItemClickListener.onMyItemClick(getLayoutPosition());
                    }
                }
            });
        }

        // связываем контент с макетом
        public void bindContentWithLayout(String content) {
            tvLessonName.setText(content);
        }
    }
}