package com.example.theerthamovers.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.Admin_module.Admin_Home;
import com.example.theerthamovers.Admin_module.Get_All_Drivers;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Pojo.Alloperator;
import com.example.theerthamovers.Pojo.GetAllOperator;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetAllOperatorsAdapter extends RecyclerView.Adapter<GetAllOperatorsAdapter.MyViewHolder> {

    Context context;
    List<Alloperator> list;

    public GetAllOperatorsAdapter(Context context, List<Alloperator> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetAllOperatorsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.get_all_drivers_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GetAllOperatorsAdapter.MyViewHolder holder, int position) {

        final Alloperator mod = list.get(position);

        Log.d("1010101010", mod.getUsername() + mod.getMobile());

        holder.ctv_name.setText(mod.getUsername());
        holder.ctv_phone.setText(mod.getMobile());

        //holder.ctv_logo.setText(mod.getUsername().substring(0, 1));

        holder.ll_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + mod.getMobile()));
                context.startActivity(intent);
            }
        });

        holder.ll_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("smsto:" + mod.getMobile());
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
               context.startActivity(Intent.createChooser(i, ""));

            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Are You Sure Want to Delete ?")
                        .setCancelable(false)
                        .setMessage("Are you sure want to delete this operator ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Retrofit retrofit = RetrofitHelper.getClient();
                                Api api = retrofit.create(Api.class);

                                api.DeleteOperator(Integer.parseInt(String.valueOf(mod.getId()))).enqueue(new Callback<JSONObject>() {
                                    @Override
                                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                                        SuccessDialogue dialogue = new SuccessDialogue(context,"Operator Successfully Deleted");
                                        FragmentManager fmm = ((FragmentActivity) context).getSupportFragmentManager();
                                        dialogue.show(fmm,"");

                                        Fragment fragment = Get_All_Drivers.newInstance("","");
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

        CustomTextView ctv_phone,ctv_name,ctv_logo;
        LinearLayout ll_whatsapp,ll_phone;
        ImageView iv_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ctv_name=itemView.findViewById(R.id.ctv_name);
            ctv_phone=itemView.findViewById(R.id.ctv_phone);
          //  ctv_logo=itemView.findViewById(R.id.ctv_logo);

            ll_whatsapp=itemView.findViewById(R.id.ll_whatsapp);
            ll_phone=itemView.findViewById(R.id.ll_phone);
            iv_delete=itemView.findViewById(R.id.iv_delete);
        }
    }
}
