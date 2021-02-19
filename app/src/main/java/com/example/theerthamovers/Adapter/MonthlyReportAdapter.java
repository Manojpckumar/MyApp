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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.Allcompletedbetweentask;
import com.example.theerthamovers.Pojo.MonthlyReportPojo;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
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

public class MonthlyReportAdapter extends RecyclerView.Adapter<MonthlyReportAdapter.MyViewHolder> {

    Context context;
    List<Allcompletedbetweentask> list;
    File pdfFile;

    public MonthlyReportAdapter() {
    }

    public MonthlyReportAdapter(Context context, List<Allcompletedbetweentask> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MonthlyReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.monthly_task_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyReportAdapter.MyViewHolder holder, int position) {

      final   Allcompletedbetweentask pojo = list.get(position);

        holder.ctv_wo_date.setText(pojo.getWorkDate());
        holder.ctv_tot_amount.setText("₹ "+String.valueOf(pojo.getTotalWorkAmount()));
        holder.ctv_veh_num.setText(pojo.getVehicleNumber());
        holder.ctv_ope_name.setText(pojo.getDriverName());
        holder.ctv_loca.setText(pojo.getLocation());

        if (pojo.getTotalWorkAmount()==0 && pojo.getTotalClientAmount()==0)
        {
            holder.ll_genclient.setVisibility(View.GONE);
            holder.ll_genadmin.setVisibility(View.GONE);
        }
        else if (pojo.getTotalWorkAmount()==0 && !(pojo.getTotalClientAmount()==0))
        {
            holder.ll_genadmin.setVisibility(View.GONE);
            holder.ll_genclient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllById id = new GetAllById(pojo.getId());
                    id.execute();

                }
            });
        }
        else
        {
            holder.ll_genclient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllById id = new GetAllById(pojo.getId());
                    id.execute();

                }
            });

            holder.ll_genadmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllByIds ids = new GetAllByIds(pojo.getId());
                    ids.execute();

                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomTextView ctv_wo_date,ctv_veh_num,ctv_ope_name,ctv_loca;
        TextView ctv_tot_amount;
        LinearLayout ll_genclient,ll_genadmin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ctv_wo_date = itemView.findViewById(R.id.ctv_wo_date);
            ctv_tot_amount = itemView.findViewById(R.id.ctv_tot_amount);
            ctv_veh_num = itemView.findViewById(R.id.ctv_veh_num);
            ctv_ope_name = itemView.findViewById(R.id.ctv_ope_name);
            ctv_loca = itemView.findViewById(R.id.ctv_locat);

            ll_genadmin = itemView.findViewById(R.id.ll_genadmin);
            ll_genclient = itemView.findViewById(R.id.ll_genclient);

        }
    }

    // Clinet Bill Download

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

                    if(clientamnt.equals(0))
                    {

                    }
                    else
                    {
                        Fetchvehicleinfo fetchvehicleinfo = new Fetchvehicleinfo(dname,vnum,wloc,stkms,endkms,trach,bata,clientamnt,wdate);
                        fetchvehicleinfo.execute();
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
            params.put("id",String.valueOf(phaseid));

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

    private boolean createPdfWrapper(String drname, String vnumber, String wloca, String startkms, String endkiloms, String transch, String batas, String vehname, String vehmodel, String vehcate, String vehpoint,String clientamnt,String wdate) throws DocumentException, FileNotFoundException {

        Date c;

        //  final File docsFolder = null;

        final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TheerthaMovers/Duplicate/Client Bill");
//        if (!docsFolder.exists()) {
//            docsFolder.mkdir();
//            Log.i("pdftag", "Created a new directory for PDF");
//        }

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

//        databaseBackupOffline(pdfname);

        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{2,2});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setFixedHeight(70);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(new PdfPCell(new Phrase("         Fields",fontH1)));
        table.addCell(new PdfPCell(new Phrase("         Values",fontH1)));

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

        table.addCell(new PdfPCell(new Phrase("Point Time",fontH2)));

        table.addCell(new PdfPCell(new Phrase(vehpoint,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Transportation Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(transch,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Bata Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(batas,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Work Date",fontH2)));

        table.addCell(new PdfPCell(new Phrase(wdate,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Total Point",fontH2)));

        double en = Double.parseDouble(endkiloms);
        double st = Double.parseDouble(startkms);
        double tot = en-st;

        table.addCell(new PdfPCell(new Phrase(String.valueOf(tot),fontH2)));

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


    // Admin Bill Download

    class GetAllByIds extends AsyncTask<Void, Void, String> {

        int phaseid;

        public GetAllByIds(Integer phaseid) {
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

                    String dname,vnum,wloc,stkms,endkms,trach,bata,clientamnt,wdate,model,others,grease,food,normaloil,hydraulicoil,spareparts,total_work_amnt,salary;

                    dname =  userJson.getString("driver_name");
                    vnum=  userJson.getString("vehicle_number");
                    wloc=  userJson.getString("location");
                    stkms=  userJson.getString("start_kms");
                    endkms=  userJson.getString("end_kms");
                    wdate=  userJson.getString("work_date");
                    trach =  userJson.getString("transportation_charge");
                    bata =  userJson.getString("bata");
                    clientamnt =  userJson.getString("total_client_amount");
                    model =  userJson.getString("model_number");
                    others =  userJson.getString("others");
                    grease =  userJson.getString("grease");
                    food =  userJson.getString("food");
                    normaloil =  userJson.getString("normal_oil");
                    hydraulicoil =  userJson.getString("hydraulic_oil");
                    spareparts =  userJson.getString("spare_parts");
                    total_work_amnt =  userJson.getString("total_work_amount");
                    salary =  userJson.getString("salary");


                    Fetchvehicleinfos fetchvehicleinfo = new Fetchvehicleinfos(dname,vnum,wloc,stkms,endkms,trach,bata,clientamnt,wdate
                            ,model,others,grease,food,normaloil,hydraulicoil,spareparts,total_work_amnt,salary);
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

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getphase1info", params);
        }
    }



    class Fetchvehicleinfos extends AsyncTask<Void, Void, String> {

        String drname,vnumber,wloca,startkms,endkiloms,transch,batas,clientamnt,wdate,model
                ,others,grease,food,normaloil,hydraulicoil,spareparts,totalworkamount,salary;

        public Fetchvehicleinfos(String dname, String vnum, String wloc, String stkms, String endkms, String trach, String bata, String clientamnt
                ,String wdate, String model, String others, String grease, String food, String normaloil,
                                 String hydraulicoil, String spareparts, String total_work_amnt, String salary) {

            this.drname=dname;
            this.vnumber=vnum;
            this.wloca=wloc;
            this.startkms=stkms;
            this.endkiloms=endkms;
            this.transch=trach;
            this.batas=bata;
            this.wdate=wdate;
            this.clientamnt = clientamnt;
            this.model = model;
            this.others = others;
            this.grease = grease;
            this.food = food;
            this.normaloil = normaloil;
            this.hydraulicoil = hydraulicoil;
            this.spareparts = spareparts;
            this.totalworkamount = total_work_amnt;
            this.salary = salary;

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
                        createPdfWrappers(drname,vnumber,wloca,startkms,endkiloms,transch,batas,vehname,vehmodel,vehcate,
                                vehpoint,clientamnt,wdate,others,grease,food,normaloil,hydraulicoil,spareparts,totalworkamount,salary);
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


    private boolean createPdfWrappers(String drname, String vnumber, String wloca, String startkms, String endkiloms,
                                      String transch, String batas, String vehname, String vehmodel, String vehcate,
                                      String vehpoint,String clientamnt,String wdate,String others,
                                      String grease,String food,String normal,String hydraulic,String spare,
                                      String totalworkamnt,String salary) throws DocumentException, FileNotFoundException {
        Date c;

        final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TheerthaMovers/Duplicate/Admin Bill");
//        if (!docsFolder.exists()) {
//            docsFolder.mkdir();
//            Log.i("pdftag", "Created a new directory for PDF");
//        }

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

        table.addCell(new PdfPCell(new Phrase("         Fields",fontH1)));
        table.addCell(new PdfPCell(new Phrase("         Values",fontH1)));

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

        table.addCell(new PdfPCell(new Phrase("Point Time",fontH2)));

        table.addCell(new PdfPCell(new Phrase(vehpoint,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Transportation Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(transch,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Bata Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(batas,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Work Date",fontH2)));

        table.addCell(new PdfPCell(new Phrase(wdate,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Total Point",fontH2)));

        double en = Double.parseDouble(endkiloms);
        double st = Double.parseDouble(startkms);
        double tot = en-st;

        table.addCell(new PdfPCell(new Phrase(String.valueOf(tot),fontH2)));

        //new items

        table.addCell(new PdfPCell(new Phrase("Vehicle Model",fontH2)));

        table.addCell(new PdfPCell(new Phrase(vehmodel,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Vehicle category",fontH2)));

        table.addCell(new PdfPCell(new Phrase(vehcate,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Other Expenses",fontH2)));

        table.addCell(new PdfPCell(new Phrase(others,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Grease",fontH2)));

        table.addCell(new PdfPCell(new Phrase(grease,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Food Expenses",fontH2)));

        table.addCell(new PdfPCell(new Phrase(food,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Normal Oil",fontH2)));

        table.addCell(new PdfPCell(new Phrase(normal,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Hydraulic Oil",fontH2)));

        table.addCell(new PdfPCell(new Phrase(hydraulic,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Spare Parts",fontH2)));

        table.addCell(new PdfPCell(new Phrase(spare,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Salary",fontH2)));

        table.addCell(new PdfPCell(new Phrase(salary,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Work Amount(Client)",fontH2)));

        table.addCell(new PdfPCell(new Phrase(clientamnt,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Total Work Amount",fontH3)));

        table.addCell(new PdfPCell(new Phrase(totalworkamnt,fontH3)));

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


}
