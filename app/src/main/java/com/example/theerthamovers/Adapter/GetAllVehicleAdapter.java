package com.example.theerthamovers.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.Admin_module.Get_All_Drivers;
import com.example.theerthamovers.Admin_module.Get_All_Vehicle;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Dialogue.SuccessDialogue;
import com.example.theerthamovers.Pojo.GetAllVehicle;
import com.example.theerthamovers.Pojo.Getvehicle;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.Api;
import com.example.theerthamovers.Retrofit.RetrofitHelper;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetAllVehicleAdapter extends RecyclerView.Adapter<GetAllVehicleAdapter.MyviewHolder> {

    Context context;
    List<Getvehicle> list;

    public GetAllVehicleAdapter(Context context, List<Getvehicle> list) {

        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public GetAllVehicleAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.get_all_vehicle_card,parent,false);

        return new MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull GetAllVehicleAdapter.MyviewHolder holder, int position) {

        final Getvehicle model = list.get(position);

        holder.ced_logo.setText(model.getVehicleName().substring(0,1));

        holder.ced_vname.setText(model.getVehicleName());
        holder.ced_vnum.setText(model.getVehicleNumber());
        holder.ced_vmodel.setText(model.getModelNo());
        holder.ced_vcate.setText(model.getCategory());

        holder.iv_deleteveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Are You Sure Want to Delete ?")
                        .setCancelable(false)
                        .setMessage("Are you sure want to delete this vehicle ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {

                                Retrofit retrofit = RetrofitHelper.getClient();
                                Api api = retrofit.create(Api.class);

                                api.DeleteVehicle(Integer.parseInt(String.valueOf(model.getId()))).enqueue(new Callback<JSONObject>() {
                                    @Override
                                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                                        SuccessDialogue dialogue = new SuccessDialogue(context,"Vehicle Successfully Deleted");
                                        FragmentManager fmm = ((FragmentActivity) context).getSupportFragmentManager();
                                        dialogue.show(fmm,"");

                                        Fragment fragment = Get_All_Vehicle.newInstance("","");
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

    public class MyviewHolder extends RecyclerView.ViewHolder {

        CustomTextView ced_vname,ced_vnum,ced_vmodel,ced_vcate,ced_logo;
        ImageView iv_deleteveh;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            ced_vname = itemView.findViewById(R.id.ced_vname);
            ced_vnum = itemView.findViewById(R.id.ced_vnum);
            ced_vmodel = itemView.findViewById(R.id.ced_vmodel);
            ced_vcate = itemView.findViewById(R.id.ced_vcate);

            ced_logo = itemView.findViewById(R.id.ced_vlogo);
            iv_deleteveh = itemView.findViewById(R.id.iv_deleteveh);
        }
    }
}
