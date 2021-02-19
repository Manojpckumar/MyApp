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
import com.example.theerthamovers.Pojo.GetCompletedTaskOperator;
import com.example.theerthamovers.R;

import java.util.List;

public class FetchCompletedTask extends RecyclerView.Adapter<FetchCompletedTask.MyViewHolder> {

    Context context;
    List<GetCompletedTaskOperator> list ;


    public FetchCompletedTask(Context context, List<GetCompletedTaskOperator> list) {

        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public FetchCompletedTask.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.today_task_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FetchCompletedTask.MyViewHolder holder, int position) {

        GetCompletedTaskOperator operator = list.get(position);

        holder.ctv_vname.setText(operator.getVehicle_name());
        holder.ctv_work_loc.setText(operator.getWork_loc());
        holder.ctv_work_date.setText(operator.getWork_date());

        if (operator.getSt_response().equals("2"))
        {
            holder.ctv_status.setText("Completed");
            holder.ctv_status.setTextColor(context.getResources().getColor(R.color.green));
            holder.iv_status.setImageResource(R.drawable.ic_status_green);
        }
        else if(operator.getSt_response().equals("3")){

            holder.ctv_status.setText("Completed");
            holder.ctv_status.setTextColor(context.getResources().getColor(R.color.green));
            holder.iv_status.setImageResource(R.drawable.ic_status_green);
        }

        holder.linear_viewdetail.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomTextView ctv_vname,ctv_work_loc,ctv_work_date,ctv_status;
        ImageView iv_status;
        LinearLayout linear_viewdetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ctv_vname = itemView.findViewById(R.id.ctv_vname);
            ctv_work_loc = itemView.findViewById(R.id.ctv_work_loc);
            ctv_work_date = itemView.findViewById(R.id.ctv_work_date);
            ctv_status = itemView.findViewById(R.id.ctv_status);

            iv_status = itemView.findViewById(R.id.iv_status);

            linear_viewdetail = itemView.findViewById(R.id.linear_viewdetail);
        }
    }
}
