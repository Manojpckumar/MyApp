package com.example.theerthamovers.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.GetAssignedTaskPoJo;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.example.theerthamovers.User_Module.GenerateBill;
import com.example.theerthamovers.User_Module.User_Phase_1;
import com.example.theerthamovers.User_Module.User_Phase_2;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TodayTaskFetch extends RecyclerView.Adapter<TodayTaskFetch.MyViewHolder> {

    String response;
    int id;
    Fragment currenthold;
    File pdfFile;

    Context context;
    List<GetAssignedTaskPoJo> list ;

    public TodayTaskFetch(Context context, List<GetAssignedTaskPoJo> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public TodayTaskFetch.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.today_task_card,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final TodayTaskFetch.MyViewHolder holder, int position) {

        final GetAssignedTaskPoJo poJo = list.get(position);

        holder.ctv_vname.setText(poJo.getVehname());
        holder.ctv_work_date.setText(poJo.getWork_date());
        holder.ctv_work_loc.setText(poJo.getWork_loc());

        response = poJo.getSt_response();
        id=poJo.getId();


        if (poJo.getSt_response().equals("0"))
        {
            holder.ctv_status.setText("Not Started");
            holder.ctv_status.setTextColor(context.getResources().getColor(R.color.red));
            holder.iv_status.setImageResource(R.drawable.ic_status_red);
        }
        else if(poJo.getSt_response().equals("1"))
        {
            holder.ctv_status.setText("Started");
            holder.ctv_status.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.iv_status.setImageResource(R.drawable.ic_status_yello);
            //holder.ctv_phaseid.setText(String.valueOf(poJo.getPhaseid()));

        }
        else
        {
            holder.ctv_status.setText("Completed");
            holder.ctv_status.setTextColor(context.getResources().getColor(R.color.green));
            holder.iv_status.setImageResource(R.drawable.ic_status_green);
        }

        // holder text change
        if (poJo.getSt_response().equals("2"))
        {
            holder.ctv_taskview.setText("Generate Bill");
        }

        if (poJo.getSt_response().equals("3"))
        {
            holder.iview_download.setImageResource(R.drawable.ic_cloud_download_black_24dp);
            holder.ctv_taskview.setText("Download Bill");
            final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TheerthaMovers");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
                Log.i("pdftag", "Created a new directory for PDF");
            }

        }
        if (poJo.getSt_response().equals("3")) {
            holder.iview_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllById id = new GetAllById(poJo.getPhaseid());
                    id.execute();

                }
            });
        }

        holder.linear_viewdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (poJo.getSt_response().equals("0"))
                {

                    LoadFragment(new User_Phase_1(String.valueOf(poJo.getId()),poJo.getOpname(),
                            poJo.getVehname(),poJo.getVehnum(),poJo.getVehmodel(),poJo.getVehcate(),
                            poJo.getWork_loc(),poJo.getWork_date(),String.valueOf(poJo.getPhaseid())),true);


                }
                else if(poJo.getSt_response().equals("1"))
                {

                    LoadFragment(new User_Phase_2(String.valueOf(poJo.getPhaseid()),String.valueOf(poJo.getId())),true);

                }


                else if(poJo.getSt_response().equals("2"))
                {
                    holder.ctv_taskview.setText("Generate Bill");
                    holder.ctv_status.setTextColor(context.getResources().getColor(R.color.green));
                    holder.iv_status.setImageResource(R.drawable.ic_status_green);
                    LoadFragment(new GenerateBill(String.valueOf(poJo.getPhaseid()),String.valueOf(poJo.getId())),false);
                }
                else
                {
                    holder.ctv_taskview.setText("Download Bill");
                    holder.ctv_taskview.setTextColor(context.getResources().getColor(R.color.green));

//                    GetAllById id = new GetAllById(poJo.getPhaseid());
//                    id.execute();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UpdateResponse extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();


//                    ((IAddNewTask) Objects.requireNonNull(getActivity())).AdddTask();

                } else {
                    Toast.makeText(context, "Invalid call", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("id",String.valueOf(id));
            params.put("started_response",response);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updataddtaskstatus", params);
        }
    }


    public void LoadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft =((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame1, fragment); // f2_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        currenthold = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomTextView ctv_vname,ctv_work_loc,ctv_work_date,ctv_status,ctv_phaseid,ctv_taskview;
        ImageView iv_status,iview_download;
        LinearLayout linear_viewdetail;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            ctv_vname=itemView.findViewById(R.id.ctv_vname);
            ctv_work_loc=itemView.findViewById(R.id.ctv_work_loc);
            ctv_work_date=itemView.findViewById(R.id.ctv_work_date);
            linear_viewdetail=itemView.findViewById(R.id.linear_viewdetail);

            ctv_status=itemView.findViewById(R.id.ctv_status);
            iv_status=itemView.findViewById(R.id.iv_status);
            ctv_phaseid=itemView.findViewById(R.id.ctv_phaseid);
            ctv_taskview=itemView.findViewById(R.id.ctv_taskview);
            iview_download=itemView.findViewById(R.id.iview_download);

        }
    }

    //getting details by id

    class GetAllById extends AsyncTask<Void, Void, String> {

        int phaseid;

        public GetAllById(Integer phaseid) {
            this.phaseid=phaseid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    String dname,vnum,wloc,stkms,endkms,trach,bata,clientamnt,wdate;

                    dname =  userJson.getString("driver_name");
                    vnum=  userJson.getString("vehicle_number");
                    wloc=  userJson.getString("location");
                    stkms=  userJson.getString("start_kms");
                    endkms=  userJson.getString("end_kms");
                    wdate=  userJson.getString("work_date");
                    trach =  userJson.getString("transportation_charge");
                    bata =  userJson.getString("bata");
                    clientamnt =  userJson.getString("total_client_amount");

                    Fetchvehicleinfo fetchvehicleinfo = new Fetchvehicleinfo(dname,vnum,wloc,stkms,endkms,trach,bata,clientamnt,wdate);
                    fetchvehicleinfo.execute();


                } else {
                    Toast.makeText(context, "Invalid call", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("id",String.valueOf(phaseid));

            Log.d("Ids is below",String.valueOf(id));

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getphase1info", params);
        }
    }




    class Fetchvehicleinfo extends AsyncTask<Void, Void, String> {

        String drname,vnumber,wloca,startkms,endkiloms,transch,batas,clientamnt,wdate;

        public Fetchvehicleinfo(String dname, String vnum, String wloc, String stkms, String endkms, String trach, String bata,String clientamnt,String wdate) {

            this.drname=dname;
            this.vnumber=vnum;
            this.wloca=wloc;
            this.startkms=stkms;
            this.endkiloms=endkms;
            this.transch=trach;
            this.batas=bata;
            this.wdate=wdate;
            this.clientamnt = clientamnt;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    String vehname,vehmodel,vehcate,vehpoint;

                    vehname =  userJson.getString("vehicle_name");
                    vehmodel =  userJson.getString("model_no");
                    vehcate =  userJson.getString("category");
                    vehpoint =  userJson.getString("point_time");

                    try {
                        createPdfWrapper(drname,vnumber,wloca,startkms,endkiloms,transch,batas,vehname,vehmodel,vehcate,vehpoint,clientamnt,wdate);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(context, "Invalid call", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("vehicle_number",vnumber);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=vehicleinfo", params);
        }
    }

    /* change working time table cell in client bill also and change it to the working hour calculated */

    private boolean createPdfWrapper(String drname, String vnumber, String wloca, String startkms, String endkiloms, String transch, String batas, String vehname, String vehmodel, String vehcate, String vehpoint,String clientamnt,String wdate) throws DocumentException, FileNotFoundException {

        Date c;
        final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TheerthaMovers");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("pdftag", "Created a new directory for PDF");
        }

        c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String date = df.format(c);

        // String na = drname.getText().toString();
        Date currentTime = Calendar.getInstance().getTime();
        String pdfnam = drname +currentTime;

        Font fontH1 = new Font(Font.FontFamily.HELVETICA, 28, Font.NORMAL);
        Font fontH2 = new Font(Font.FontFamily.HELVETICA, 23, Font.NORMAL);
        Font fontH3 = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD);
        Font fontH4 = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);

        final String pdfname = pdfnam +".pdf";


        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{2,2});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setFixedHeight(70);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(new PdfPCell(new Phrase("        Fields",fontH1)));
        table.addCell(new PdfPCell(new Phrase("        Values",fontH1)));

        table.addCell(new PdfPCell(new Phrase("Driver Name",fontH2)));

        table.addCell(new PdfPCell(new Phrase(drname,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Vehicle Number",fontH2)));

        table.addCell(new PdfPCell(new Phrase(vnumber,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Work Location",fontH2)));

        table.addCell(new PdfPCell(new Phrase(wloca,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Vehicle Name",fontH2)));

        table.addCell(new PdfPCell(new Phrase(vehname,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Start Reading",fontH2)));

        table.addCell(new PdfPCell(new Phrase(startkms,fontH2)));

        table.addCell(new PdfPCell(new Phrase("End Reading",fontH2)));

        table.addCell(new PdfPCell(new Phrase(endkiloms,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Working Time ",fontH2)));


        double en = Double.parseDouble(endkiloms);
        double st = Double.parseDouble(startkms);
        float tot = (float) (en-st);

        String doubleAsString = String.valueOf(tot);


        int indexOfDecimal = doubleAsString.indexOf(".");


        if (doubleAsString.substring(indexOfDecimal).equals(".0"))
        {

            String worktim = doubleAsString.substring(0, indexOfDecimal)+"Hours";

            table.addCell(new PdfPCell(new Phrase(worktim,fontH2)));


        }
        else
        {
            int vh = Integer.parseInt(vehpoint);
            Double t =Double.parseDouble(doubleAsString.substring(indexOfDecimal))*10*vh ;
            Log.d("sdf",String.valueOf(t) );
            String worktim = doubleAsString.substring(0, indexOfDecimal)+" Hours and "+t+" Minitues";

            table.addCell(new PdfPCell(new Phrase(worktim,fontH2)));
        }

        table.addCell(new PdfPCell(new Phrase("Transportation Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(transch,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Bata Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(batas,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Work Date",fontH2)));

        table.addCell(new PdfPCell(new Phrase(wdate,fontH2)));




        table.addCell(new PdfPCell(new Phrase("Work Amount",fontH3)));

        table.addCell(new PdfPCell(new Phrase(clientamnt,fontH3)));

        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(new BaseColor(98,155,193));
        }

        PdfWriter.getInstance(document, output);
        document.open();
        Paragraph paragraph1 = new Paragraph();
        Font f = new Font(Font.FontFamily.HELVETICA, 22.0f, Font.UNDERLINE, BaseColor.BLACK);
        Font g = new Font(Font.FontFamily.HELVETICA, 30.0f, Font.BOLD, BaseColor.BLACK);
        //  document.add(new Paragraph("                               Theertha Earth Movers Chittilappilly : 9497565758" + "\n \n"));
        document.add(new Paragraph("Theertha Earth Movers Chittilappilly : 9497565758 \n \n",fontH4));
        paragraph1.setSpacingAfter(85f);
        document.add(new Paragraph("", g));
        document.add(table);

        document.close();
        // Log.e("safiya", MyList1.toString());
        //  previewPdf();

        new AlertDialog.Builder(context)
                .setTitle("Downloaded Successfully")
                .setMessage("Your pdf successfully downloaded. Click share to share the pdf through whatsapp")
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sharetowhatsapp(docsFolder, pdfname);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_status_green)
                .show();
        return true;
    }

    private void sharetowhatsapp(File docsFolder, String pdfname) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File outputFile = new File(docsFolder, pdfname);
        Uri uri = Uri.fromFile(outputFile);

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setPackage("com.whatsapp");

        context.startActivity(share);

    }
}
