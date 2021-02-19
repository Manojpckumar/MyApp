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

import com.example.theerthamovers.Admin_module.AdminBillFragment;
import com.example.theerthamovers.Admin_module.ClinetBillFragment;
import com.example.theerthamovers.Admin_module.DownloadBillFragment;
import com.example.theerthamovers.Admin_module.UpdateAllDataFragment;
import com.example.theerthamovers.Admin_module.UpdateDataFrag;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.AllassignedtasksComple;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
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

public class GetAllCompletedWork extends RecyclerView.Adapter<GetAllCompletedWork.MyViewHolder> {

    File pdfFile;
    List<AllassignedtasksComple> list ;
    Context context;

    public GetAllCompletedWork(Context context, List<AllassignedtasksComple> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetAllCompletedWork.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.admin_recieving_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GetAllCompletedWork.MyViewHolder holder, int position) {

        final AllassignedtasksComple work = list.get(position);

        holder.ctv_driname.setText(work.getDriverName());
        holder.ctv_statuss.setText("COMPLETED");
        holder.ctv_statuss.setTextColor(context.getResources().getColor(R.color.green));
        holder.ctv_vhname.setText(work.getVehicleName());
        holder.ctv_wdate.setText(work.getWorkDate());
        holder.ctv_wplace.setText(work.getWorkLocation());

        if (work.getBillStatus().equals("1"))
        {

            holder.iv_admin_bill.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_account_box_black_24dp));

            holder.ll_bill.setVisibility(View.GONE);
            holder.ll_buttons.setVisibility(View.VISIBLE);

            holder.ll_client_bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllById id = new GetAllById(work.getPhaseid());
                    id.execute();

                }
            });

            // generate admin bill
            holder.ll_admin_bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment = AdminBillFragment.newInstance(String.valueOf(work.getPhaseid()),String.valueOf(work.getId()));
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.frame1,fragment);
                    ft.commit();

                }
            });

        }

        if (work.getBillStatus().equals("2"))
        {
            holder.ll_bill.setVisibility(View.GONE);
            holder.ll_buttons.setVisibility(View.VISIBLE);

            holder.ctv_admin_bill.setText("ADMIN BILL");
           // holder.iv_admin_bill.setBackgroundResource(R.drawable.ic_cloud_download_black_24dp);
            holder.iv_admin_bill.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_cloud_download_black_24dp));

            holder.ll_admin_bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllByIds ids = new GetAllByIds(work.getPhaseid());
                    ids.execute();
                }
            });

            holder.ll_client_bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllById id = new GetAllById(work.getPhaseid());
                    id.execute();

                }
            });

        }

        holder.ll_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (work.getBillStatus().equals(""))
                {
                    Fragment fragment = UpdateAllDataFragment.newInstance(String.valueOf(work.getPhaseid()),String.valueOf(work.getId()));
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.frame1,fragment);
                    ft.commit();
                }

                else if (work.getBillStatus().equals("1"))
                {
                    Fragment fragment = UpdateDataFrag.newInstance(String.valueOf(work.getPhaseid()),String.valueOf(work.getId()));
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.frame1,fragment);
                    ft.commit();
                }

                else if (work.getBillStatus().equals("1"))
                {
                    Fragment fragment = ClinetBillFragment.newInstance(String.valueOf(work.getId()),String.valueOf(work.getPhaseid()));
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.frame1,fragment);
                    ft.commit();
                }

                else if (work.getBillStatus().equals("2"))
                {
                    Fragment fragment = AdminBillFragment.newInstance(String.valueOf(work.getId()),String.valueOf(work.getPhaseid()));
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.frame1,fragment);
                    ft.commit();
                }
                else
                {
                    Fragment fragment = DownloadBillFragment.newInstance(String.valueOf(work.getPhaseid()),String.valueOf(work.getId()));
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.frame1,fragment);
                    ft.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_back,ll_bill,ll_buttons,ll_admin_bill,ll_client_bill;
        CustomTextView ctv_wplace,ctv_vhname,ctv_wdate,ctv_driname,ctv_statuss,ctv_bill_stat,ctv_admin_bill;
        ImageView iv_bill_stat,iv_admin_bill;

        public MyViewHolder(@NonNull View view) {
            super(view);

            ctv_driname = view.findViewById(R.id.ctv_driname);
            ctv_statuss = view.findViewById(R.id.ctv_statuss);
            ctv_vhname = view.findViewById(R.id.ctv_vhname);
            ctv_wdate = view.findViewById(R.id.ctv_wdate);
            ctv_wplace = view.findViewById(R.id.ctv_wplace);
            ctv_bill_stat = view.findViewById(R.id.ctv_bill_stat);
            ctv_admin_bill = view.findViewById(R.id.ctv_admin_bill);

            ll_buttons = view.findViewById(R.id.ll_buttons);
            ll_back = view.findViewById(R.id.ll_back);
            ll_bill = view.findViewById(R.id.ll_bill);
            ll_admin_bill = view.findViewById(R.id.ll_admin_bill);
            ll_client_bill = view.findViewById(R.id.ll_client_bill);

            iv_bill_stat = view.findViewById(R.id.iv_bill_stat);
            iv_admin_bill = view.findViewById(R.id.iv_admin_bill);

        }
    }



//    private boolean databaseBackupOffline(String pdfname) {
//        String inFileName = pdfname;
//         final  String EXTERNAL_LOCATION= Environment.getExternalStorageDirectory() + "/TheerthaMovers";
//        return fileCopy(inFileName,EXTERNAL_LOCATION);
//    }
//
//    private boolean fileCopy(String from,String to){
//        FileInputStream fis;
//        OutputStream output;
//        try {
//            File dbFile = new File(from);
//            fis = new FileInputStream(dbFile);
//            String outFileName = to;
//            output = new FileOutputStream(outFileName);
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = fis.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//            output.flush();
//            output.close();
//            fis.close();
//            return true;
//        } catch (IOException e) {
//            Toast.makeText(context, "Error there", Toast.LENGTH_SHORT).show();;
//        } finally {
//
//        }
//        return false;
//    }


    private boolean createPdfWrapper(String drname, String vnumber, String wloca, String startkms, String endkiloms, String transch, String batas, String vehname, String vehmodel, String vehcate, String vehpoint,String clientamnt,String wdate) throws DocumentException, FileNotFoundException {

        Date c;

      //  final File docsFolder = null;

       final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TheerthaMovers/Main/Client Bill");
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

        table.addCell(new PdfPCell(new Phrase("Total Working Time",fontH2)));


        double en = Double.parseDouble(endkiloms);
        double st = Double.parseDouble(startkms);
        double tot = en-st;

        // double doubleNumber = 114.6;
        String doubleAsString = String.valueOf(tot);
        int indexOfDecimal = doubleAsString.indexOf(".");
        Log.d("0000001515151","Double Number: " + tot);
        Log.d("0000001515151","Integer Part: " + doubleAsString.substring(0, indexOfDecimal));
        Log.d("0000001515151","Decimal Part: " + doubleAsString.substring(indexOfDecimal));

        if (doubleAsString.substring(indexOfDecimal).equals(".0"))
        {

            String worktim = doubleAsString.substring(0, indexOfDecimal)+"Hours";

            table.addCell(new PdfPCell(new Phrase(worktim,fontH2)));
            table.addCell(new PdfPCell(new Phrase(worktim,fontH2)));

            Log.d("value2255","Decimal Part: " +worktim);
        }
        else
        {
            int val =  Integer.parseInt(doubleAsString.substring(indexOfDecimal));

            int t = Integer.parseInt(vehpoint);
            int minitues = val*t;

            String worktim = doubleAsString.substring(0, indexOfDecimal)+"Hours and"+minitues+"Minitues";

            Log.d("Work Time fullum",worktim);

            //   worktim = intArr[0]+"Hours and"+intArr[1]*6+"minitues";

            table.addCell(new PdfPCell(new Phrase(worktim,fontH2)));
        }



        table.addCell(new PdfPCell(new Phrase(vehpoint,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Transportation Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(transch,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Bata Charge",fontH2)));

        table.addCell(new PdfPCell(new Phrase(batas,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Work Date",fontH2)));

        table.addCell(new PdfPCell(new Phrase(wdate,fontH2)));

//        table.addCell(new PdfPCell(new Phrase("Total Point",fontH2)));
//
//        double en = Double.parseDouble(endkiloms);
//        double st = Double.parseDouble(startkms);
//        double tot = en-st;
//
//        table.addCell(new PdfPCell(new Phrase(String.valueOf(tot),fontH2)));

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



    // admin bill

    private boolean createPdfWrappers(String drname, String vnumber, String wloca, String startkms, String endkiloms,
                                      String transch, String batas, String vehname, String vehmodel, String vehcate,
                                      String vehpoint,String clientamnt,String wdate,String others,
                                      String grease,String food,String normal,String hydraulic,String spare,
                                      String totalworkamnt,String salary) throws DocumentException, FileNotFoundException {
        Date c;

        final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TheerthaMovers/Main/Admin Bill");
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


}
