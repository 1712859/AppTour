package com.example.tourviet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import android.os.FileUtils;

public class Main3Activity_changeAvatar extends AppCompatActivity {

    Button back,choose,save;
    ImageView image;
    String token;
    String filePath = null,linkimage;


    StorageTask uploadtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_change_avatar);


        Anhxa();
        controlbutton();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //...............................
        if (bundle != null) {
            token = bundle.getString("Key_1");
        }


    }

    private void Anhxa() {
        back = (Button)findViewById(R.id.backge);
        choose = (Button)findViewById(R.id.buttonChose);
        save = (Button)findViewById(R.id.buttonSave);
        image = (ImageView) findViewById(R.id.avatar);

    }

    private void controlbutton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity_changeAvatar.this,Main2Activity_user.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadtask != null && uploadtask.isInProgress()) {
                    Toast.makeText(Main3Activity_changeAvatar.this,"upploading........",Toast.LENGTH_LONG).show();
                } else {
                    uploadImage();
                }
            }
        });
    }
    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0) {


            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    linkimage = ConvertBitmapToString(bitmap);
                    image.setImageBitmap(bitmap);



                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public static String ConvertBitmapToString(Bitmap bitmap){
        String encodedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            encodedImage= URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedImage;
    }

    private void uploadImage() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userClient client = retrofit.create((userClient.class));
        Call<Avatar> call = client.UppdateAvatar(token,linkimage);
        call.enqueue(new Callback<Avatar>() {
            @Override
            public void onResponse(Call<Avatar> call, Response<Avatar> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main3Activity_changeAvatar.this, response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Main3Activity_changeAvatar.this,Main2Activity_user.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Key_1", token);
                intent.putExtras(bundle1);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Avatar> call, Throwable t) {

            }
        });




    }
}

