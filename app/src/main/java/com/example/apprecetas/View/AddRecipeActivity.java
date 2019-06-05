package com.example.apprecetas.View;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.apprecetas.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;

public class AddRecipeActivity extends AppCompatActivity {
    ArrayList<String>nameImages = new ArrayList<>();
    Button selectButton;
    String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}; //Permisos!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);

        selectButton = (Button) findViewById(R.id.button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
    }


    public void SelectImage() {
        final CharSequence[] options = {"Select from gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipeActivity.this);
        builder.setTitle("Select");
        builder.setCancelable(true);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Capture image")) {
                    if (EasyPermissions.hasPermissions(AddRecipeActivity.this, galleryPermissions)) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "newTest.jpg");
                        f.setReadable(true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }else {
                        EasyPermissions.requestPermissions(this, "Access for storage",
                                101, galleryPermissions);
                    }

                } else if (options[item].equals("Select from gallery")) {
                    if (EasyPermissions.hasPermissions(AddRecipeActivity.this, galleryPermissions)) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else {
                        EasyPermissions.requestPermissions(this, "Access for storage",
                                101, galleryPermissions);
                    }
                }
            }
        });
        builder.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File newTest : f.listFiles()) {
                    if (newTest.getName().equals("newTest.jpg")) {
                        f = newTest;
                        break;
                    }
                }

                try {


                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    f.delete();
                    OutputStream outFile = null;
                    final File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {

                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final  String pat= getRealPathFromURI(Uri.fromFile(file).toString());
                    Log.e("PRINTPATH", pat);
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                uploadImageToAWS(pat);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                final String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = null;
                thumbnail = (BitmapFactory.decodeFile(picturePath));
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            uploadImageToAWS(picturePath);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }
    }



    private void uploadImageToAWS(String selectedImagePath) {

        if (selectedImagePath == null) {
            Toast.makeText(this, "Could not find the filepath of the selected file", Toast.LENGTH_LONG).show();

            return;
        }
        File file = new File(selectedImagePath);
        AmazonS3 s3Client = null;
        if (s3Client == null) {

            ClientConfiguration clientConfig = new ClientConfiguration();

            clientConfig.setProtocol(Protocol.HTTPS);

            clientConfig.setMaxErrorRetry(0);

            clientConfig.setSocketTimeout(60000);
            System.out.println("uno:"+MenuActivity.aws_id+",dos:"+ MenuActivity.aws_passw);
            BasicAWSCredentials credentials = new BasicAWSCredentials(MenuActivity.aws_id, MenuActivity.aws_passw);
            System.out.println("uno:"+MenuActivity.aws_id+",dos:"+ MenuActivity.aws_passw);
            s3Client = new AmazonS3Client(credentials, clientConfig);

            s3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
        }

        FileInputStream stream = null;

        try {

            stream = new FileInputStream(file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            Log.d("messge", "converting to bytes");
            objectMetadata.setContentLength(file.length());
            String[] s = selectedImagePath.split("\\.");
            String extenstion = s[s.length - 1];
            Log.d("messge", "set content length : " + file.length() + "sss" + extenstion);
            String fileName = UUID.randomUUID().toString().replace("-","");

            PutObjectRequest putObjectRequest = new PutObjectRequest("progralenguajes",  fileName + "." + "JPG", stream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = s3Client.putObject(putObjectRequest);
            nameImages.add("'"+fileName+"'");
            if (result == null) {

                System.out.println("RESULT "+ "NULL");

            } else {

                System.out.println("RESULT"+ result.toString());
            }

        } catch (Exception e) {

            System.out.println("ERROR"+ " " + e.getMessage());
            e.printStackTrace();
        }

    }


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }

    }
    public String getNameReceta(){
        EditText ed = findViewById(R.id.txt_name);
        String res = ed.getText().toString();
        return res;
    }
    public String getTypeReceta(){
        EditText ed = findViewById(R.id.txt_type);
        String res = ed.getText().toString();
        return res;
    }
    public String getPasosReceta(){
        EditText ed = findViewById(R.id.text_pasos);
        String res = ed.getText().toString();
        return res;
    }
    public String getIngredientes(){
        EditText ed = findViewById(R.id.text_ing);
        String res = ed.getText().toString();
        return res;
    }
    public String pasosString(String s){
        int cont =0;
        String acu= "";
        ArrayList<String> arr = new ArrayList<>();
        while(cont< s.length()){
            if(s.charAt(cont) != '\n'){
                acu += s.charAt(cont);
                cont++;
            }
            else {
                cont++;
                arr.add("'"+acu+"'");
                acu = "";
            }
        }
        arr.add("'"+acu+"'");
        String res = arr.toString().replace(", ",",");
        return res;
    }
    public String ingredString(String s){
        int cont = 0;
        String acu ="";
        s.replace(", ",",");
        s.replace(" ,",",");
        ArrayList<String> arr = new ArrayList<>();
        while(cont< s.length()){
            if(s.charAt(cont) != ','){
                acu += s.charAt(cont);
                cont++;
            }
            else {
                cont++;
                arr.add("'"+acu+"'");
                acu = "";
            }
        }
        arr.add("'"+acu+"'");
        String res = arr.toString().replace(", ",",");
        return res;
    }



    public void agregarReceta(View v){
        String name = getNameReceta();
        String type = getTypeReceta();
        String pasos = pasosString(getPasosReceta());
        String ingred = ingredString(getIngredientes());
        String img = nameImages.toString().replace(", ",",");
        System.out.println("---------------IMAG:"+img);
        try {
            String api = "https://api-receta.herokuapp.com/";
            String receta = "comida("+"'"+name+"'"+","+ingred+","+"'"+type+"'"+","+pasos+","+img+")."+"&auth="+LoginActivity.authKey;
            System.out.println(receta);
            URL url = new URL(api + "agregarReceta?receta="+receta);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder b = new StringBuilder();
            String input;
            while ((input = br.readLine()) != null){
                b.append(input);
            }
            String resul = b.toString();
            if (resul.equals( "Modified")){
                Toast.makeText(AddRecipeActivity.this, "Agregado!", Toast.LENGTH_LONG).show();
                nameImages.clear();
                callMenu(findViewById(android.R.id.content));

            }
            else{
                Toast.makeText(AddRecipeActivity.this, "No se pudo agregar!", Toast.LENGTH_LONG).show();

            }
            br.close();
            urlConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void callMenu(View v){
        startActivity(new Intent(AddRecipeActivity.this,MenuActivity.class));
    }

}




