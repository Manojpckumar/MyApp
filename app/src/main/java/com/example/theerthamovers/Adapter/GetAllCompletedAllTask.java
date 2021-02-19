package com.example.theerthamovers.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.StatusinfoPending;
import com.example.theerthamovers.Pojo.Statusinfocompleted;
import com.example.theerthamovers.R;

import java.util.List;

public class GetAllCompletedAllTask extends RecyclerView.Adapter<GetAllCompletedAllTask.MyViewholder> {

    Context context;
    List<Statusinfocompleted> list;

    public GetAllCompletedAllTask(Context context, List<Statusinfocompleted> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetAllCompletedAllTask.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.admin_recieving_card,parent,false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllCompletedAllTask.MyViewholder holder, int position) {

        Statusinfocompleted mod = list.get(position);

        holder.ll_bill.setVisibility(View.GONE);

        holder.ctv_driname.setText(mod.getDriverName());
        holder.ctv_wdate.setText(mod.getWorkDate());
        holder.ctv_vhname.setText(mod.getVehicleName());
        holder.ctv_wplace.setText(mod.getWorkLocation());
        holder.ctv_statuss.setTextColor(context.getResources().getColor(R.color.green));
        holder.ctv_statuss.setText("COMPLETED");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        CustomTextView ctv_wplace,ctv_vhname,ctv_wdate,ctv_driname,ctv_statuss,ctv_bill_stat,ctv_admin_bill;
        LinearLayout ll_bill;
        public MyViewholder(@NonNull View view) {
            super(view);

            ctv_driname = view.findViewById(R.id.ctv_driname);
            ctv_statuss = view.findViewById(R.id.ctv_statuss);
            ctv_vhname = view.findViewById(R.id.ctv_vhname);
            ctv_wdate = view.findViewById(R.id.ctv_wdate);
            ctv_wplace = view.findViewById(R.id.ctv_wplace);
            ctv_bill_stat = view.findViewById(R.id.ctv_bill_stat);
            ctv_admin_bill = view.findViewById(R.id.ctv_admin_bill);
            ll_bill = view.findViewById(R.id.ll_bill);
        }
    }
}
