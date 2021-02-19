package com.example.theerthamovers.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.Admin_module.GetAllPendingTasks;
import com.example.theerthamovers.Admin_module.Get_All_Drivers;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Pojo.AllAssignedTasksNew;
import com.example.theerthamovers.Pojo.StatusinfoPending;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetAllPendingAllTasks extends RecyclerView.Adapter<GetAllPendingAllTasks.MyViewHolder> {

    Context context;
    List<StatusinfoPending> list;

    public GetAllPendingAllTasks(Context context, List<StatusinfoPending> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetAllPendingAllTasks.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.allpendinglayout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllPendingAllTasks.MyViewHolder holder, int position) {

        final StatusinfoPending work = list.get(position);

        holder.ctv_driname.setText(work.getDriverName());
        holder.ctv_statuss.setText("PENDING");
        holder.ctv_statuss.setTextColor(context.getResources().getColor(R.color.red));
        holder.ctv_vhname.setText(work.getVehicleName());
        holder.ctv_wdate.setText(work.getWorkDate());
        holder.ctv_wplace.setText(work.getWorkLocation());

        holder.ll_bill.setVisibility(View.GONE);

        holder.ll_back.setBackgroundColor(context.getResources().getColor(R.color.red));

        holder.ctv_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Are You Sure Want to Delete ?")
                        .setCancelable(false)
                        .setMessage("Are you sure want to delete this Work ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Retrofit retrofit = RetrofitHelper.getClient();
                                Api api = retrofit.create(Api.class);

                                api.DeletePending(work.getId()).enqueue(new Callback<JSONObject>() {
                                    @Override
                                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                                        SuccessDialogue dialogue = new SuccessDialogue(context,"Work Successfully Deleted");
                                        FragmentManager fmm = ((FragmentActivity) context).getSupportFragmentManager();
                                        dialogue.show(fmm,"");

                                        Fragment fragment = GetAllPendingTasks.newInstance("","");
                                        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.replace(R.id.frame1,fragment);
                                        ft.commit();

                                    }

                                    @Override
                                    public void onFailure(Call<JSONObject> call, Throwable t) {

                                    }
                                });


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.ic_delete_red_24dp)
                        .show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_back,ll_bill;
        CustomTextView ctv_wplace,ctv_vhname,ctv_wdate,ctv_driname,ctv_statuss,ctv_bill_stat;
        ImageView iv_bill_stat,ctv_id;

        public MyViewHolder(@NonNull View view) {
            super(view);

            ctv_driname = view.findViewById(R.id.ctv_drinames);
            ctv_statuss = view.findViewById(R.id.ctvs_statuss);
            ctv_vhname = view.findViewById(R.id.ctv_vhnames);
            ctv_wdate = view.findViewById(R.id.ctv_wdates);
            ctv_wplace = view.findViewById(R.id.ctv_wplaces);
            ll_back = view.findViewById(R.id.ll_backs);
            ll_bill = view.findViewById(R.id.ll_bills);
            iv_bill_stat = view.findViewById(R.id.iv_bill_stats);
            ctv_bill_stat = view.findViewById(R.id.ctv_bill_stats);
            ctv_id = view.findViewById(R.id.ctv_id);
        }
    }
}
