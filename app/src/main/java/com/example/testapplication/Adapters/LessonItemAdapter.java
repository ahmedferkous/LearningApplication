package com.example.testapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Items.LessonItem;
import com.example.testapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LessonItemAdapter extends RecyclerView.Adapter<LessonItemAdapter.ViewHolder> {
    private static final String TAG = "LessonItemAdapter";
    public static final String BEGINNER = "Beginner";
    public static final String INTERMEDIATE = "Intermediate";
    public static final String ADVANCED = "Advanced";

    private ArrayList<LessonItem> lessonItems = new ArrayList<>();
    private final Context context;

    public LessonItemAdapter(Context context) {
        this.context = context;
    }

    public void setLessonItems(ArrayList<LessonItem> lessonItems) {
        this.lessonItems = lessonItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LessonItem boundItem = lessonItems.get(position);

        holder.txtDifficulty.setText(boundItem.getDifficulty());
        holder.txtDueTime.setText(boundItem.getDatePosted());
        holder.txtLessonName.setText(boundItem.getLessonName());
        holder.txtLessonDesc.setText(boundItem.getLessonDesc());

        holder.imgViewDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgViewDropdown.setVisibility(View.GONE);
                holder.imgViewDropup.setVisibility(View.VISIBLE);
                holder.txtLessonDesc.setVisibility(View.VISIBLE);
            }
        });

        holder.imgViewDropup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgViewDropdown.setVisibility(View.VISIBLE);
                holder.imgViewDropup.setVisibility(View.GONE);
                holder.txtLessonDesc.setVisibility(View.GONE);
            }
        });

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + boundItem.getFileUris().get(0).getPath());
            }
        });

        switch(boundItem.getDifficulty()) {
            case BEGINNER:
                holder.imgViewBackground.setBackgroundColor(context.getResources().getColor(R.color.darkGreen));
                break;
            case INTERMEDIATE:
                holder.imgViewBackground.setBackgroundColor(context.getResources().getColor(R.color.lightBlue));
                break;
            case ADVANCED:
                holder.imgViewBackground.setBackgroundColor(context.getResources().getColor(R.color.lightRed));
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return lessonItems.size();
    }

    private static String calculateDurationLeft(String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String inputString1 = getCurrentDate();

        try {
            Date firstDate = sdf.parse(inputString1);
            Date secondDate = sdf.parse(inputDate);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return String.valueOf(diff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDate;
    }

    private static String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout parent;
        private final TextView txtDifficulty, txtDueTime, txtLessonName, txtLessonDesc;
        private final ImageView imgViewDropdown, imgViewDropup, imgViewBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtDifficulty = itemView.findViewById(R.id.txtDifficulty);
            txtDueTime = itemView.findViewById(R.id.txtDueTime);
            txtLessonName = itemView.findViewById(R.id.txtLessonName);
            txtLessonDesc = itemView.findViewById(R.id.txtLessonDesc);
            imgViewDropdown = itemView.findViewById(R.id.imgViewDropdown);
            imgViewDropup = itemView.findViewById(R.id.imgViewDropup);
            imgViewBackground = itemView.findViewById(R.id.imgViewBackground);
        }
    }
}
