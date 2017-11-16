package abrv0765.shoppingcart;

import android.view.View;

import com.google.gson.Gson;
import com.itextpdf.text.Document;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PdfGeneration extends AppCompatActivity {

    int count=0;
    private ArrayList<String> item_name;
    private ArrayList<Integer> item_quantity;
    private ArrayList<Integer> item_price;

    PdfPCell cell1[];
    PdfPCell cell2[];
    PdfPCell cell3[];
    PdfPCell cell4[];


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_generation);
        verifyStoragePermissions(this);
        Bundle bun=getIntent().getExtras();
        Gson g=new Gson();



//        item_name=g.fromJson(itemname,ArrayList.class);
//        item_price=g.fromJson(itemprice,ArrayList.class);
        item_name=(ArrayList<String>)getIntent().getSerializableExtra("item_name");
        item_price=(ArrayList<Integer>)getIntent().getSerializableExtra("item_price");
        item_quantity=(ArrayList<Integer>)getIntent().getSerializableExtra("item_quantity");
         cell1=new PdfPCell[item_name.size()];
         cell2=new PdfPCell[item_name.size()];
         cell3=new PdfPCell[item_name.size()];
         cell4=new PdfPCell[item_name.size()];
        Log.v("hee",item_name.size()+""+item_price.size());


    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    public  void createPdf(View view) throws FileNotFoundException, DocumentException {
if(item_price.size()==0||item_quantity.size()==0||item_name.size()==0)
    return;
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "pdfdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            Log.i("done", "Pdf Directory created");
        }

        //Create time stamp
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        File myFile = new File(pdfFolder + timeStamp + ".pdf");

        OutputStream output = new FileOutputStream(myFile);
        Document document=new Document();

        PdfWriter pd=PdfWriter.getInstance(document,output);
        document.open();
        document.addTitle(" Bill Invoice ");
        document.add(new Paragraph("Hello There"));
        PdfPTable pt=new PdfPTable(4);
        pt.setWidthPercentage(105);
        pt.setSpacingBefore(11f);
        pt.setSpacingAfter(11f);
        Paragraph p= new Paragraph("Bill Invoice");
        p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        document.add(p);
        float colWidth[]= {2f,2f,2f,2f};
        pt.setWidths(colWidth);

        PdfPCell cell12=new PdfPCell(new Paragraph("Item Name"));
        PdfPCell cell22=new PdfPCell(new Paragraph("Quantity"));
        PdfPCell cell32=new PdfPCell(new Paragraph("Price"));
        PdfPCell cell42=new PdfPCell(new Paragraph("Total"));

        pt.addCell(cell12);
        pt.addCell(cell22);
        pt.addCell(cell32);
        pt.addCell(cell42);

        for(int i=0;i<cell1.length;i++) {

            pt.addCell(item_name.get(i));
            pt.addCell(" "+item_quantity.get(i));
            pt.addCell(""+item_price.get(i));
            pt.addCell(""+item_price.get(i)*item_quantity.get(i));
            count=count+item_price.get(i)*item_quantity.get(i);
        }


        PdfPCell cella=new PdfPCell(new Paragraph("Total Amount"));
        PdfPCell cellb=new PdfPCell(new Paragraph(""));
        PdfPCell cellc=new PdfPCell(new Paragraph(""));
        PdfPCell celld=new PdfPCell(new Paragraph(""+count));
        pt.addCell(cella);
        pt.addCell(cellb);
        pt.addCell(cellc);
        pt.addCell(celld);

        document.add(pt);
        document.close();
        pd.close();
        Toast.makeText(this, "Pdf generated", Toast.LENGTH_SHORT).show();


    }
//    private void viewPdf(){
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(myFile), "application/pdf");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);
//    }











}
