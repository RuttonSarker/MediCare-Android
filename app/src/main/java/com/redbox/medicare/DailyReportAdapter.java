package com.redbox.medicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.redbox.medicare.Fragments.DailyReportFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.MyViewHolder> {

    ArrayList<DailyReport> arrayList;

    Context context;

    public DailyReportAdapter(Context context, ArrayList<DailyReport> arrayList, DailyReportFragment dailyReportFragment) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @NotNull
    @Override
    public DailyReportAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_daily_report_recycleview,parent,false);
        final DailyReportAdapter.MyViewHolder myViewHolder = new DailyReportAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DailyReportAdapter.MyViewHolder holder, int position) {

        final DailyReport dailyReport = arrayList.get(position);
        final String date = arrayList.get(position).getDate();
        final String bp = arrayList.get(position).getBloodPressure();
        final String db = arrayList.get(position).getDiabetesTest();


        holder.date.setText(date);
        holder.bp.setText(bp);
        holder.dp.setText(db);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, bp, dp;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date= itemView.findViewById(R.id.date);
            bp = itemView.findViewById(R.id.bloodPressure);
            dp = itemView.findViewById(R.id.diabetes);
        }
    }
}
