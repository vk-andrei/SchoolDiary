package com.example.schooldiary.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooldiary.R;
import com.example.schooldiary.repository.CardData;
import com.example.schooldiary.repository.CardsSource;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

    private CardsSource cardsSource;
    private OnMyItemClickListener onMyItemClickListener;
    private Fragment fragmentDiary;
    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }

    // Можно так (и лучше так)
    public void setData(CardsSource cardsSource) {
        this.cardsSource = cardsSource;
        notifyDataSetChanged(); // команда адаптеру отрисовать все(!) полученные данные
    }

    // а можно и в конструктор
    public DiaryAdapter(CardsSource cardsSource) {
        this.cardsSource = cardsSource;
    }

    public DiaryAdapter(Fragment fragmentDiary) {
        this.fragmentDiary = fragmentDiary;
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
        holder.bindContentWithLayout(cardsSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return cardsSource.size();
        //return 99999999;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvLessonName;
        private final TextView tvLessonDescription;
        private final ImageView imgLesson;
        private final ToggleButton chkLesson;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tv_lesson_name);
            tvLessonDescription = itemView.findViewById(R.id.tv_lesson_description);
            imgLesson = itemView.findViewById(R.id.img_lesson_image);
            chkLesson = itemView.findViewById(R.id.chk_lesson);

            fragmentDiary.registerForContextMenu(itemView);

            itemView.setOnLongClickListener(view -> {
                menuPosition = getLayoutPosition();
                return false;
            });
            // для кликабельных элементов
            imgLesson.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    //view.showContextMenu(100, 100);
                    return false;
                }
            });


            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMyItemClickListener != null) {
                        onMyItemClickListener.onMyItemClick(getLayoutPosition());
                    }
                }
            });*/
        }

        // связываем контент с макетом
        @SuppressLint("SetTextI18n")
        public void bindContentWithLayout(CardData content) {
            tvLessonName.setText(content.getTitle());
            tvLessonDescription.setText(content.getDescription() + " " + content.getDate());
            imgLesson.setImageResource(content.getImage());
            chkLesson.setChecked(content.isLike());
        }
    }
}