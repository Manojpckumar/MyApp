package com.example.theerthamovers.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.Allcompletedinc;
import com.example.theerthamovers.R;

import java.util.ArrayList;
import java.util.List;

public class GetAllSumByDate extends RecyclerView.Adapter<GetAllSumByDate.MyViewHolder> {

    Context context;
    List<Allcompletedinc> list = new ArrayList<>();

    public GetAllSumByDate() {
    }

    public GetAllSumByDate(Context context, List<Allcompletedinc> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetAllSumByDate.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sum_card_detail,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull GetAllSumByDate.MyViewHolder holder, int position) {

        Allcompletedinc model = list.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomTextView ctv_itemname,ctv_itemsum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ctv_itemname=itemView.findViewById(R.id.ctv_itemname);
            ctv_itemsum=itemView.findViewById(R.id.ctv_itemsum);
        }
    }
}
