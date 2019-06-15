package com.example.carrental;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.carrental.Interface.CarRentalsAPI;

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
    public static final int PICK_IMAGE=1;
    Retrofit retrofit;
    CarRentalsAPI carRentalsAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

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
                openGallery();
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
            imageUri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageCar.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
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
