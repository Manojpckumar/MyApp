package com.example.theerthamovers.Admin_module;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theerthamovers.CustomViews.CustomButton;
import com.example.theerthamovers.CustomViews.CustomEditText;
import com.example.theerthamovers.CustomViews.CustomTextView;
import com.example.theerthamovers.Pojo.IncomeByDatePojo;
import com.example.theerthamovers.Pojo.Vehicle;
import com.example.theerthamovers.R;
import com.example.theerthamovers.Retrofit.RequestHandler;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClinetBillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinetBillFragment extends Fragment {

    java.util.Date c;
    LinearLayout ll_total_card;
    private File pdfFile;
    CustomButton cbn_calculate,cbn_generateBill;
    ImageView iv_download,iv_share;
    CustomEditText ced_rate;
    CustomTextView ctv_drname,ctv_vehno,ctv_loca,ctv_vehname,ctv_startread,
                   ctv_endread,ctv_point,ctv_trancharge,ctv_bata,ctv_tot_pnt,ctv_wamount,ctv_trancost,ctv_batach,ctv_total;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String idd;
    private String phidd;

    public ClinetBillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ClinetBillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClinetBillFragment newInstance(String idd, String phidd) {
        ClinetBillFragment fragment = new ClinetBillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, idd);
        args.putString(ARG_PARAM2, phidd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idd = getArguments().getString(ARG_PARAM1);
            phidd = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clinet_bill, container, false);

    //    Log.d("printmsg000",mParam1+mParam2);

        ctv_drname = view.findViewById(R.id.ctv_drname);
        ctv_endread = view.findViewById(R.id.ctv_endread);
        ctv_vehno = view.findViewById(R.id.ctv_vehno);
        ctv_loca = view.findViewById(R.id.ctv_loca);
        ctv_vehname = view.findViewById(R.id.ctv_vehname);
        ctv_startread = view.findViewById(R.id.ctv_startread);
        ctv_point = view.findViewById(R.id.ctv_point);
        ctv_trancharge = view.findViewById(R.id.ctv_trancharge);
        ctv_bata = view.findViewById(R.id.ctv_bata);
        ctv_tot_pnt = view.findViewById(R.id.ctv_tot_pnt);

        iv_download = view.findViewById(R.id.iv_download);
        iv_share = view.findViewById(R.id.iv_shares);

        ctv_wamount = view.findViewById(R.id.ctv_wamount);
        ctv_trancost = view.findViewById(R.id.ctv_trancost);
        ctv_batach = view.findViewById(R.id.ctv_batach);
        ctv_total = view.findViewById(R.id.ctv_total);

        ced_rate = view.findViewById(R.id.ced_rate);
        ll_total_card = view.findViewById(R.id.ll_total_card);

        cbn_calculate = view.findViewById(R.id.cbn_calculate);
        cbn_generateBill = view.findViewById(R.id.cbn_generateBill);

        GetAllById all = new GetAllById();
        all.execute();

        cbn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = ced_rate.getText().toString();

                if (validate(t))
                {
                    ll_total_card.setVisibility(View.VISIBLE);

                    double end = (Double.parseDouble(ctv_endread.getText().toString()));
                    double start = (Double.parseDouble(ctv_startread.getText().toString()));
                    double point = (Double.parseDouble(ctv_point.getText().toString()));
                    double rate= (Double.parseDouble(ced_rate.getText().toString()));

                    float total = (float) (end - start);

                    ctv_tot_pnt.setText(String.valueOf(total)+"Points");

                    double workcharge = total*rate;
                    ctv_wamount.setText(String.format("%.2f",workcharge));

                    ctv_trancost.setText(ctv_trancharge.getText());
                    ctv_batach.setText(ctv_bata.getText());

                    double trancost,batach;
                    batach = Double.parseDouble(ctv_batach.getText().toString());
                    trancost = Double.parseDouble(ctv_trancost.getText().toString());

                    double total_amount = workcharge+trancost+batach;

                    ctv_total.setText(String.format("%.2f",total_amount));


                    ctv_total.requestFocus();

                    cbn_generateBill.setVisibility(View.VISIBLE);

                }
                else
                {
                    Toast.makeText(getContext(), "Enter Rate Per Hour", Toast.LENGTH_SHORT).show();

                }

                cbn_generateBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        iv_download.setVisibility(View.VISIBLE);
                        UpdateClientAmount amount = new UpdateClientAmount();
                        amount.execute();

                    }
                });


            }
        });

        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    createPdfWrapper();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }



    private boolean validate(String t) {

        if (t.isEmpty())
        {
            Toast.makeText(getContext(), "Enter Rate Per Hour", Toast.LENGTH_SHORT).show();
        }
        else
        {
            return true;
        }

        return false;
    }

    class GetAllById extends AsyncTask<Void, Void, String> {

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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    ctv_drname.setText(userJson.getString("driver_name"));
                    ctv_vehno.setText(userJson.getString("vehicle_number"));
                    ctv_loca.setText(userJson.getString("location"));
                    ctv_startread.setText(userJson.getString("start_kms"));
                    ctv_endread.setText(userJson.getString("end_kms"));
                    ctv_trancharge.setText(userJson.getString("transportation_charge"));
                    ctv_bata.setText(userJson.getString("bata"));

                    if (!ctv_vehno.getText().equals(""))
                    {
                        Fetchvehicleinfo fetchvehicleinfo = new Fetchvehicleinfo();
                        fetchvehicleinfo.execute();
                    }

                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
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
            params.put("id",phidd);

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=getphase1info", params);
        }
    }

    class Fetchvehicleinfo extends AsyncTask<Void, Void, String> {

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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("veh");

                    //creating a new user object
                    Vehicle ve = new Vehicle(
                            userJson.getString("vehicle_name"),
                            userJson.getString("model_no"),
                            userJson.getString("category"),
                            userJson.getString("point_time")
                    );

                    ctv_vehname.setText(ve.getVehicle_number());
                    ctv_point.setText(ve.getPoint_Time());

                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
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
            params.put("vehicle_number",ctv_vehno.getText().toString());

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/Api.php?apicall=vehicleinfo", params);
        }
    }
    // updating bill status to 2 for client
    class UpdateBillStatus extends AsyncTask<Void, Void, String> {

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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
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
            params.put("id",idd);
            params.put("bill_status","2");

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updatebillstatus", params);
        }
    }


    class UpdateClientAmount extends AsyncTask<Void, Void, String> {

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
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    UpdateBillStatus status = new UpdateBillStatus();
                    status.execute();

                } else {
                    Toast.makeText(getActivity(), "Invalid call", Toast.LENGTH_SHORT).show();
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
            params.put("id",phidd);
            params.put("total_client_amount",ctv_total.getText().toString());

            return requestHandler.sendPostRequest("https://sysirohub.com/theerthaearthmovers/phase1/v1/Api.php?apicall=updateclientamount", params);
        }
    }

    private boolean createPdfWrapper() throws DocumentException, FileNotFoundException {

        final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TheerthaMovers");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("pdftag", "Created a new directory for PDF");
        }

        c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String date = df.format(c);

       String na = ctv_drname.getText().toString();
        Date currentTime = Calendar.getInstance().getTime();
       String pdfnam = na+currentTime;

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
        String dname = ctv_drname.getText().toString();
        table.addCell(new PdfPCell(new Phrase(dname,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Vehicle Number",fontH2)));
        String veno = ctv_vehno.getText().toString();
        table.addCell(new PdfPCell(new Phrase(veno,fontH2)));


        table.addCell(new PdfPCell(new Phrase("Work Location",fontH2)));
        String wloc = ctv_loca.getText().toString();
        table.addCell(new PdfPCell(new Phrase(wloc,fontH2)));


        table.addCell(new PdfPCell(new Phrase("Vehicle Name",fontH2)));
        String vename = ctv_vehname.getText().toString();
        table.addCell(new PdfPCell(new Phrase(vename,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Start Reading",fontH2)));
        String startread = ctv_startread.getText().toString();
        table.addCell(new PdfPCell(new Phrase(startread,fontH2)));

        table.addCell(new PdfPCell(new Phrase("End Reading",fontH2)));
        String endread = ctv_endread.getText().toString();
        table.addCell(new PdfPCell(new Phrase(endread,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Total Working Time",fontH2)));
        double en = Double.parseDouble(ctv_endread.getText().toString());
        double st = Double.parseDouble(ctv_startread.getText().toString());
        float tot = (float) (en-st);

        String doubleAsString = String.valueOf(tot);

        int indexOfDecimal = doubleAsString.indexOf(".");

        if (doubleAsString.substring(indexOfDecimal).equals(".0"))
        {

            String worktim = doubleAsString.substring(0, indexOfDecimal)+"Hours";

            table.addCell(new PdfPCell(new Phrase(worktim,fontH2)));


            Log.d("value2255","Decimal Part: " +worktim);
        }
        else
        {
            int vh = Integer.parseInt(ctv_point.getText().toString());
            Double t =Double.parseDouble(doubleAsString.substring(indexOfDecimal))*10*vh ;
            Log.d("sdf",String.valueOf(t) );
            String worktim = doubleAsString.substring(0, indexOfDecimal)+" Hours and "+t+" Minitues";

            table.addCell(new PdfPCell(new Phrase(worktim,fontH2)));

        }

        table.addCell(new PdfPCell(new Phrase("Transportation Charge",fontH2)));
        String trasnchar = ctv_trancost.getText().toString();
        table.addCell(new PdfPCell(new Phrase(trasnchar,fontH2)));


        table.addCell(new PdfPCell(new Phrase("Bata Charge",fontH2)));
        String bata = ctv_batach.getText().toString();
        table.addCell(new PdfPCell(new Phrase(bata,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Work_Amount(per/hour)",fontH2)));
        String amnt = ced_rate.getText().toString();
        table.addCell(new PdfPCell(new Phrase(amnt,fontH2)));


        table.addCell(new PdfPCell(new Phrase("Work Amount",fontH2)));
        String workamnt = ctv_wamount.getText().toString();
        table.addCell(new PdfPCell(new Phrase(workamnt,fontH2)));

        table.addCell(new PdfPCell(new Phrase("Total Amount",fontH3)));
        String total = ctv_total.getText().toString();
        table.addCell(new PdfPCell(new Phrase(total,fontH3)));

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
        document.add(new Paragraph("Theertha Earth Movers Chittilappilly : 9497565758 \n \n",fontH4));
        paragraph1.setSpacingAfter(85f);
        document.add(new Paragraph("", g));
        document.add(table);

        document.close();


        new AlertDialog.Builder(getActivity())
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

        getActivity().startActivity(share);

    }

}
