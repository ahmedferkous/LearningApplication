package com.example.testapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.Activities.LoginClass;
import com.example.testapplication.Activities.TestActivity;
import com.example.testapplication.Items.TestItem;
import com.example.testapplication.R;
import com.example.testapplication.ViewModels.LoginViewModel;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TestItemAdapter extends RecyclerView.Adapter<TestItemAdapter.ViewHolder> {
    private static final String TAG = "TestItemAdapter";

    private ArrayList<TestItem> testItems = new ArrayList<>();
    private final LoginViewModel loginViewModel;
    private final Context context;

    public TestItemAdapter(LoginViewModel loginViewModel, Context context) {
        this.loginViewModel = loginViewModel;
        this.context = context;
    }

    public void setTestItems(ArrayList<TestItem> testItems) {
        this.testItems = testItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestItem boundTestItem = testItems.get(position);

        holder.txtTestName.setText(boundTestItem.getTestName());
        holder.txtTestDescription.setText(boundTestItem.getTestDescription());
        holder.txtProgressPercentage.setText(boundTestItem.getAssignedUser().getProgress()+"%");
        holder.progressBar.setProgress(Integer.parseInt(boundTestItem.getAssignedUser().getProgress()));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testActivityIntent = new Intent(context, TestActivity.class);
                testActivityIntent.putExtra(LoginClass.USER_ID_KEY, loginViewModel.userID.getValue());
                testActivityIntent.putExtra(LoginClass.MD5_PASSWORD_KEY, loginViewModel.password.getValue());
                testActivityIntent.putExtra(TestActivity.TEST_ITEM_KEY, boundTestItem);
                context.startActivity(testActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout parent;
        private final TextView txtTestName, txtTestDescription, txtProgressPercentage;
        private final CircularProgressIndicator progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtTestName = itemView.findViewById(R.id.txtTestName);
            txtTestDescription = itemView.findViewById(R.id.txtDescription);
            txtProgressPercentage = itemView.findViewById(R.id.txtProgressPercentage);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
