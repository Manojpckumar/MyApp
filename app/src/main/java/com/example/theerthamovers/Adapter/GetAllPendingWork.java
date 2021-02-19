package com.example.theerthamovers.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.AllAssignedTasksNew;
import com.example.theerthamovers.Pojo.StatusinfoPending;
import com.example.theerthamovers.Pojo.Statusinfocompleted;
import com.example.theerthamovers.R;

import java.util.List;

public class GetAllPendingWork extends RecyclerView.Adapter<GetAllPendingWork.MyViewHolder> {

    Context context;
    List<AllAssignedTasksNew> list;


    public GetAllPendingWork(Context context, List<AllAssignedTasksNew> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public GetAllPendingWork.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_recieving_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllPendingWork.MyViewHolder holder, int position) {

        AllAssignedTasksNew work = list.get(position);

        holder.ctv_driname.setText(work.getDriverName());
        holder.ctv_statuss.setText("PENDING");
        holder.ctv_statuss.setTextColor(context.getResources().getColor(R.color.red));
        holder.ctv_vhname.setText(work.getVehicleName());
        holder.ctv_wdate.setText(work.getWorkDate());
        holder.ctv_wplace.setText(work.getWorkLocation());
        holder.ll_bill.setVisibility(View.GONE);

        holder.ll_back.setBackgroundColor(context.getResources().getColor(R.color.red));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_back,ll_bill;
        CustomTextView ctv_wplace,ctv_vhname,ctv_wdate,ctv_driname,ctv_statuss,ctv_bill_stat;
        ImageView iv_bill_stat;

        public MyViewHolder(@NonNull View view) {
            super(view);

            ctv_driname = view.findViewById(R.id.ctv_driname);
            ctv_statuss = view.findViewById(R.id.ctv_statuss);
            ctv_vhname = view.findViewById(R.id.ctv_vhname);
            ctv_wdate = view.findViewById(R.id.ctv_wdate);
            ctv_wplace = view.findViewById(R.id.ctv_wplace);
            ll_back = view.findViewById(R.id.ll_back);
            ll_bill = view.findViewById(R.id.ll_bill);
            iv_bill_stat = view.findViewById(R.id.iv_bill_stat);
            ctv_bill_stat = view.findViewById(R.id.ctv_bill_stat);
        }
    }
}
