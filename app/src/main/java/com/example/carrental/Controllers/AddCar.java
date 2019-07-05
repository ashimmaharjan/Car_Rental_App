package com.example.carrental.Controllers;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.carrental.Interface.CarRentalsAPI;
import com.example.carrental.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCar extends AppCompatActivity {


    ImageView imageCar;
    EditText carName,carMan,carSeats,carMileage,carRentalPrice,carImageName;
    RadioGroup AC_Status;
    Button btnInsertCar;
    Uri imageUri;
    Bitmap bitmap;
    private static final int PICK_IMAGE=1;
    private static final int CAPTURE_IMAGE=2;
    Retrofit retrofit;
    CarRentalsAPI carRentalsAPI;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Admin Dashboard");
        actionBar.setSubtitle("Add Car");

        imageCar=findViewById(R.id.inputCarImage);
        carName=findViewById(R.id.inputCarName);
        carMan=findViewById(R.id.inputCarMan);
        carSeats=findViewById(R.id.inputSeats);
        carMileage=findViewById(R.id.inputMileage);
        carRentalPrice=findViewById(R.id.inputRentalPrice);
        carImageName=findViewById(R.id.inputImageName);

        AC_Status=findViewById(R.id.AC_Status);
        AC_Status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.statusYes)
                {
                    AC_Status.setTag("Yes");
                }
                else if(checkedId==R.id.statusNo)
                {
                    AC_Status.setTag("No");
                }
            }
        });

        btnInsertCar=findViewById(R.id.insertCar);

        imageCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();

                AlertDialog.Builder builder=new AlertDialog.Builder(AddCar.this);
                builder.setTitle("Choose an option");

                String[] options={"Open Camera","Choose from gallery"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0: //for camera
                                Intent openCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(openCamera,CAPTURE_IMAGE);
                                break;

                            case 1://for gallery
                                openGallery();
                                break;
                        }
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        btnInsertCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(bitmap);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addCar();
                    }
                }, 1100);
            }
        });



    }

    private void createInstance()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:6969")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carRentalsAPI=retrofit.create(CarRentalsAPI.class);
    }

    private void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }


    public void openGallery()
    {
        Intent gallery=new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery,"Choose Image"),PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            if (data==null)
            {
                Toast.makeText(this, "No image selected. Please select a image.", Toast.LENGTH_SHORT).show();
            }
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageCar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode==CAPTURE_IMAGE && resultCode==RESULT_OK)
        {
            Bundle extras=data.getExtras();
            bitmap=(Bitmap)extras.get("data");
            imageCar.setImageBitmap(bitmap);
        }
    }

    private void saveImage(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes=stream.toByteArray();

        try {
            File file=new File(this.getCacheDir(),"image.jpeg");
            file.createNewFile();
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part body=MultipartBody.Part.createFormData("carImage",file.getName(),requestBody);

            createInstance();
            carRentalsAPI=retrofit.create(CarRentalsAPI.class);
            Call<String> imageCall=carRentalsAPI.uploadImage(body);
            imageCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(AddCar.this,response.body(), Toast.LENGTH_SHORT).show();
                    carImageName.setText(response.body());

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(AddCar.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCar()
    {
        String cName=carName.getText().toString();
        String cMan=carMan.getText().toString();
        String cAC=AC_Status.getTag().toString();
        String cSeats=carSeats.getText().toString();
        String cMileage=carMileage.getText().toString();
        String cRPrice=carRentalPrice.getText().toString();
        String cImageName=carImageName.getText().toString();
        createInstance();

        Call<String> carCall=carRentalsAPI.addCar(cName,cMan,cAC,cSeats,cMileage,cRPrice,cImageName);
        carCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(AddCar.this, "Items added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddCar.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
