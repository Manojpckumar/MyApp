package com.example.theerthamovers.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.IncomeByDatePojo;
import com.example.theerthamovers.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetIncomByDateAdapter extends RecyclerView.Adapter<GetIncomByDateAdapter.MyViewHolder> {

    List<IncomeByDatePojo> list = new ArrayList<>();
    Context context;

    public GetIncomByDateAdapter(List<IncomeByDatePojo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public GetIncomByDateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.expenses_by_date,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetIncomByDateAdapter.MyViewHolder holder, int position) {

        IncomeByDatePojo pojo = list.get(position);
        holder.tv_head.setText(pojo.getCategory());
        holder.tv_amt.setText("Rs "+pojo.getAmount());
        Picasso.with(context).load(Integer.valueOf(pojo.getDrawable())).into(holder.iv_imag);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomTextView tv_head,tv_amt;
        ImageView iv_imag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_imag=itemView.findViewById(R.id.iv_imag);
            tv_head=itemView.findViewById(R.id.tv_head);
            tv_amt=itemView.findViewById(R.id.tv_amt);
        }
    }
}
